package com.bonial.newsapp.database

import com.bonial.newsapp.getDateBeforeOrAfter
import com.bonial.newsapp.model.NewsItem
import java.util.*

object TempUtils {

    fun populateObjects(date: Date, count: Int = 21, before: Boolean): MutableList<NewsItem> {
        val list = mutableListOf<NewsItem>()
        var i = 0
        while(i < count) {
            list.add(NewsItem(count.toString(), date.getDateBeforeOrAfter(i, before), "jopa", "jopa"))
            i++
        }
        return list
    }
}