package com.bonial.newsapp.database

import android.content.Context
import com.bonial.newsapp.model.NewsItem
import javax.inject.Inject

class DatabaseService @Inject constructor (val context: Context): DatabaseProvider {

    private val daoActions: DaoActions = AppDatabase.getDatabase(context)!!.daoActions()

    override suspend fun getDataFromDatabase(): List<NewsItem?>? {
        val data = daoActions.getAll()
        if (data != null) {
            return data
        }
        return null
    }

    override suspend fun submitDataToDatabase(data: MutableList<NewsItem?>?): Boolean {
        if (data != null) {
            daoActions.deleteAll()
            daoActions.insert(data)
            return true
        }
        return false
    }
}