package com.bonial.newsapp.database

import com.bonial.newsapp.model.NewsItem

interface DatabaseProvider {

    suspend fun getDataFromDatabase(): List<NewsItem?>?

    suspend fun submitDataToDatabase(data: MutableList<NewsItem?>?): Boolean

}