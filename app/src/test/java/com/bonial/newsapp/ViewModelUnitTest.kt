package com.bonial.newsapp

import com.bonial.newsapp.model.NewsItem
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ViewModelUnitTest: BaseUnitTest() {

    private lateinit var viewModel: NewsFeedViewModel

    private val allItems = mutableListOf<NewsItem?>()
    private val newItems = mutableListOf<NewsItem>()

    @Before
    fun setup () {
        viewModel = NewsFeedViewModel()
    }

    @Test
    fun appendMostRecentToItemsList_appendedAndSorted() {
        //case 1
        allItems.addAll(populateObjects(21,true))
        newItems.addAll(populateObjects(4,false).asReversed())
        var mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(mostRecent.size == 4)
        assertTrue(allItems.size == 25)

        //case 2
        allItems.clear()
        newItems.clear()
        allItems.addAll(populateObjects(30,true))
        newItems.addAll(populateObjects(2,false).asReversed())
        newItems.addAll(allItems.filterNotNull().subList(0, 5))
        mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(mostRecent.size == 2)
        assertTrue(allItems.size == 32)

        //case 3
        allItems.clear()
        newItems.clear()
        allItems.addAll(populateObjects(38,true))
        newItems.addAll(mutableListOf())
        newItems.addAll(allItems.filterNotNull().subList(0, 34))
        mostRecent = viewModel.appendMostRecentToItemsList(newItems.toList(), allItems)
        assertTrue(allItems.size == 38)
        assertTrue(mostRecent.size == 0)
    }

    private fun populateObjects(count: Int = 21, before: Boolean): MutableList<NewsItem> {
        val list = mutableListOf<NewsItem>()
        var i = 0
        while(i < count) {
            val fDate = SimpleDateFormat(NewsFeedViewModel.FORMATTER).format(Date().getDateBeforeOrAfter(1, before))
            list.add(NewsItem(fDate, UUID.randomUUID().toString(),
                "Abcd", "no", "Abc news", "Content", "url"))
            i++
        }
        return list
    }
}
