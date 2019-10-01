package com.bonial.newsapp

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.ObservableMessage
import com.bonial.newsapp.retrofit.RequestNewsCalls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestPresenter(val managedCoroutineScope: ManagedCoroutineScope, val apiMethods: RequestNewsCalls) {

    private var _news = MutableLiveData<Pair<ObservableMessage, List<NewsItem?>>>()
    val news: LiveData<Pair<ObservableMessage, List<NewsItem?>>>
        get() = _news

    val allNews = mutableListOf<NewsItem?>()
    var selectedItem: NewsItem? = null

    private var _error = MutableLiveData<Pair<ObservableMessage, String>>()
    val error: LiveData<Pair<ObservableMessage, String>>
        get() = _error

    var allEarliestLoaded = false
    private var loadedAmount = 0

    val dataLoaded: ObservableBoolean = object : ObservableBoolean() {
        override fun get(): Boolean {
            return allNews.isNotEmpty()
        }
    }

    fun getDataFromWebServer(before: Boolean) {
        managedCoroutineScope.launch(Dispatchers.IO){
            val recentNews = apiMethods.getRecentNews()
            if (recentNews != null) {
                if (before) {
                    loadedAmount = recentNews.size
                    if (allNews.isNotEmpty()) {
                        allNews.clear()
                    } else {
                        if (!dataLoaded.get()) {
                            dataLoaded.set(true)
                        }
                    }
                    allNews.addAll(recentNews)

                    _news.postValue(Pair(
                        ObservableMessage(), recentNews.subList(0,
                            NewsFeedViewModel.AMOUNT_TO_LOAD
                        )))
                } else {
//                    val tempList = appendMostRecentToItemsList(recentNews, allNews)
//                    _news.postValue(Pair(first = ObservableMessage(alreadyReceived = false, appendingData = true
//                    ), second = tempList
//                    ))
                }

            } else {
                _error.postValue(Pair(
                    ObservableMessage(),
                    NewsFeedViewModel.MESSAGE_SERVER_SIDE_ERROR
                ))
            }
        }
    }
}