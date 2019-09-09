package com.bonial.newsapp.retrofit

import com.bonial.newsapp.model.NewsItem

interface RequestNewsCalls {

    suspend fun getRecentNews(): List<NewsItem>?
}