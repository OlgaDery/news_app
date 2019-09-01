package com.bonial.newsapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.newsapp.database.DatabaseService
import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.retrofit.RequestNewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
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
    }

    private var _news = MutableLiveData<Pair<Int, List<NewsItem?>>>()
    val news: LiveData<Pair<Int, List<NewsItem?>>>
        get() = _news

    private var _error = MutableLiveData<Pair<Int, String>>()
    val error: LiveData<Pair<Int, String>>
        get() = _error

    var newsObservableID = 0
    var errorObservableID = 0

    private val dataID: String
    get() {
        return "NEWS".plus("_").plus(Date().toString())
    }

    var allEarliestLoaded = false
    var loadedAmount = 0
    val allRecentLoaded = true

    //TODO variable to store single value

    fun getDataFromWebServer(dateFrom: Date, before: Boolean) {

        //TODO
        viewModelScope.launch(Dispatchers.IO){
            val recentNews = apiMethods.getRecentNews()
            if (recentNews != null) {
                loadedAmount = recentNews.size
                if (recentNews.size < AMOUNT_TO_LOAD) {
                    allEarliestLoaded = false
                }
                if (news.value == null) {
                    _news.postValue(Pair(newsObservableID + 1, recentNews))
                } else {
                    val newList = mutableListOf<NewsItem?>()
                    newList.addAll(news.value!!.second)
                    newList.addAll(recentNews)
                    _news.postValue(Pair(newsObservableID + 1, newList))
                }
            } else {
                _error.postValue(Pair(errorObservableID + 1, MESSAGE_SERVER_SIDE_ERROR))
            }
        }
    }

    fun getDataFromDB() {
        viewModelScope.launch(Dispatchers.IO){
            val list: List<NewsItem?>? = databaseMethods.getDataFromDatabase(dataID, NewsItem::class.java)
            if (!list.isNullOrEmpty()) {
                loadedAmount = list.size
                val pair = Pair(newsObservableID + 1, list.toMutableList())
                _news.postValue(pair)

            } else {
                if (appContext.checkNetwork() == 0) {
                    _error.postValue(Pair(errorObservableID + 1, MESSAGE_NO_CONNECTION))
                }
            }
        }
        if (appContext.checkNetwork() != 0) {
            getDataFromWebServer(Date(), false)
        } else {
            _error.postValue(Pair(errorObservableID + 1, MESSAGE_NO_CONNECTION))
        }
    }

    fun updateDatabase(mutableList: List<NewsItem?>) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseMethods.submitDataToDatabase(NewsItem::class.java, mutableList, List::class.java, dataID)
        }
    }

}