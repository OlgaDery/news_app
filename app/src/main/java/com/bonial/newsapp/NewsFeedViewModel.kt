package com.bonial.newsapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.newsapp.database.DatabaseService
import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.ObservableMessage
import com.bonial.newsapp.retrofit.RequestNewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel: ViewModel() {

    @Inject
    lateinit var apiMethods: RequestNewsService

    @Inject
    lateinit var databaseMethods: DatabaseService

    @Inject
    lateinit var appContext: Context

    companion object {
        const val AMOUNT_TO_LOAD = 21
        const val MESSAGE_NO_CONNECTION = "no connection"
        const val MESSAGE_SERVER_SIDE_ERROR = "server side error"
        const val DATABASE_KEY = "news"
        const val PORTRAIT_MODE = "portrait"
        const val LANDSCAPE_MODE = "landscape"
    }

    private var _news = MutableLiveData<Pair<ObservableMessage, List<NewsItem?>>>()
    val news: LiveData<Pair<ObservableMessage, List<NewsItem?>>>
        get() = _news

    val allNews = mutableListOf<NewsItem?>()
    var selectedItem: NewsItem? = null

    private var _error = MutableLiveData<Pair<ObservableMessage, String>>()
    val error: LiveData<Pair<ObservableMessage, String>>
        get() = _error


    private val dataID: String
    get() {
        return DATABASE_KEY
    }

    var allEarliestLoaded = false
    var loadedAmount = 0

    fun getDataFromWebServer(before: Boolean) {
        viewModelScope.launch(Dispatchers.IO){
            val recentNews = apiMethods.getRecentNews()
            if (recentNews != null) {
                if (before) {
                    loadedAmount = recentNews.size
                    if (allNews.isNotEmpty()) {
                        allNews.clear()
                    }
                    allNews.addAll(recentNews)

                    _news.postValue(Pair(ObservableMessage(), recentNews.subList(0, AMOUNT_TO_LOAD)))
                } else {
                    val tempList = appendMostRecentToItemsList(recentNews, allNews)
                    _news.postValue(Pair(ObservableMessage(false, true), tempList))
                }

            } else {
                _error.postValue(Pair(ObservableMessage(), MESSAGE_SERVER_SIDE_ERROR))
            }
        }
    }

    fun updateNewsList(fetchFromIndex: Int) {
        val end: Int
        if (allNews.subList(fetchFromIndex, allNews.size-1).size < AMOUNT_TO_LOAD) {
            end = allNews.size
            allEarliestLoaded = true
        } else {
            end = fetchFromIndex + AMOUNT_TO_LOAD
        }
        _news.value = Pair(ObservableMessage(false, true), allNews.subList(fetchFromIndex, end))

    }

    fun getDataFromDB() {
        viewModelScope.launch(Dispatchers.IO){
            val list: List<NewsItem?>? = databaseMethods.getDataFromDatabase(dataID, NewsItem::class.java)
            if (!list.isNullOrEmpty()) {
                loadedAmount = list.size
                val pair = Pair(ObservableMessage(), list.toMutableList().subList(0, 21))
                _news.postValue(pair)
                allNews.addAll(list.toMutableList())
            }
        }
        if (appContext.checkNetwork() != 0) {
            getDataFromWebServer(true)
        } else {
            _error.postValue(Pair(ObservableMessage(), MESSAGE_NO_CONNECTION))
        }
    }

    fun updateDatabase(mutableList: List<NewsItem?>) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseMethods.submitDataToDatabase(NewsItem::class.java, mutableList, List::class.java, dataID)
        }
    }

    fun appendMostRecentToItemsList(itemsToAppend: List<NewsItem>?, all: MutableList<NewsItem?>): MutableList<NewsItem> {
        var count = 0
        val tempList = mutableListOf<NewsItem>()
        if (itemsToAppend!!.isNotEmpty()) {
            itemsToAppend.forEach {
                if (it != all[0] && it.publishedAt.after(all[0]!!.publishedAt)) {
                    System.out.println(it.publishedAt.after(all[0]!!.publishedAt))
                    tempList.add(count, it)
                    count++
                } else {
                    return@forEach
                }
            }
            all.addAll(0, tempList)
        }
        return tempList

    }

}