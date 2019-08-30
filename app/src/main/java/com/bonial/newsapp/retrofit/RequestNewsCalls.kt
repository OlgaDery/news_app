package com.bonial.newsapp.retrofit

import com.bonial.newsapp.model.NewsItem
import retrofit2.Call
import java.util.*

interface RequestNewsCalls {

    //TODO getting 21 record
    suspend fun getRecentNews(date: Date = Date()): List<NewsItem>?
}