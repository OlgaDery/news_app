package com.bonial.newsapp.retrofit

import com.bonial.newsapp.model.NewsItem
import java.util.*

interface RequestNewsCalls {

    //TODO getting 21 record
    suspend fun getRecentNews(): List<NewsItem>?
}