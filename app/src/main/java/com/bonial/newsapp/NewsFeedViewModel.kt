package com.bonial.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.newsapp.database.DatabaseService
import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.retrofit.RequestNewsService
import com.bonial.newsapp.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class NewsFeedViewModel: ViewModel() {

    @Inject
    lateinit var apiMethods: RequestNewsService

    @Inject
    lateinit var databaseMethods: DatabaseService

    //TODO variable to store all the feed
    private var _news = MutableLiveData<Pair<Int, List<NewsItem?>>>()

    val news: LiveData<Pair<Int, List<NewsItem?>>>
        get() = _news

    var observableID = 0

    val dataID: String
    get() {
        return "NEWS".plus("_").plus(Date().toString())
    }

    //TODO variable to store single value

    fun getDataFromWebServer(dateFrom: Date) {
        viewModelScope.launch(Dispatchers.IO){
            val recentNews = apiMethods.getRecentNews(dateFrom)
            if (recentNews != null) {
                if (news.value == null) {
                    _news.postValue(Pair(observableID + 1, recentNews))
                } else {
                    val newList = mutableListOf<NewsItem?>()
                    newList.addAll(news.value!!.second)
                    newList.addAll(recentNews)
                    _news.postValue(Pair(observableID + 1, newList))
                }
                updateDatabase(news.value!!.second)
            }
        }
    }

    fun getDataFromDB() {
        viewModelScope.launch(Dispatchers.IO){
            val list: List<NewsItem?>? = databaseMethods.getDataFromDatabase(dataID, NewsItem::class.java)
            if (!list.isNullOrEmpty()) {
                val pair = Pair(observableID + 1, list.toMutableList())
                _news.postValue(pair)

            } else {
//                if (context.checkNetwork() == 0) {
//                    error.postValue(NO_CONNECTION_MESSAGE)
//                }
            }
        }
//        if (context.checkNetwork() != 0) {
            getDataFromWebServer(Date())
//        } else {
//            error.postValue(NO_CONNECTION_MESSAGE)
//        }
    }

    fun updateDatabase(mutableList: List<NewsItem?>) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseMethods.submitDataToDatabase(NewsItem::class.java, mutableList, List::class.java, dataID)
        }
    }

}