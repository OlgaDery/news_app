package com.bonial.newsapp

import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.Source
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

class ViewModelUnitTest {
    lateinit var viewModel: NewsFeedViewModel
    val allItems = mutableListOf<NewsItem?>()
    val newItems = mutableListOf<NewsItem>()

    @Before
    fun setup () {
        viewModel = NewsFeedViewModel()
    }

    @Test
    fun appendMostRecentToItemsList_appendedAndSorted() {
        //case 1
        allItems.addAll(populateObjects(Date(), 21,true))
        newItems.addAll(populateObjects(Date(), 4,false).asReversed())
        var mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(mostRecent.size == 4)
        assertTrue(allItems.size == 25)

        //case 2
        allItems.clear()
        newItems.clear()
        allItems.addAll(populateObjects(Date(), 30,true))
        newItems.addAll(populateObjects(Date(), 2,false).asReversed())
        newItems.addAll(allItems.filterNotNull().subList(0, 5))
        mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(mostRecent.size == 2)
        assertTrue(allItems.size == 32)
//
//        //case 3
        allItems.clear()
        newItems.clear()
        allItems.addAll(populateObjects(Date(), 38,true))
        newItems.addAll(mutableListOf())
        newItems.addAll(allItems.filterNotNull().subList(0, 34))
        mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(allItems.size == 38)
        assertTrue(mostRecent.size == 0)
    }

    fun populateObjects(date: Date, count: Int = 21, before: Boolean): MutableList<NewsItem> {
        val list = mutableListOf<NewsItem>()
        var i = 0
        while(i < count) {
            list.add(NewsItem(date.getDateBeforeOrAfter(i, before), UUID.randomUUID().toString(),
                "Abcd", "no", "Abc news", "Content", "url", Source("abc", "")
            ))
            i++
        }
        return list
    }
}
