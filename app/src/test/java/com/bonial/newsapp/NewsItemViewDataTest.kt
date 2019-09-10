package com.bonial.newsapp

import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.NewsItemViewData
import com.bonial.newsapp.model.Source
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsItemViewDataTest: BaseUnitTest() {

    @Before
    fun setup () {
        `when`(mockResources.getString(R.string.from)).thenReturn("From")
        `when`(mockResources.getString(R.string.hours_ago)).thenReturn("hours ago")
    }

    @Test
    fun checkGeneratedValues() {
        val titleValue = "Adcdef"
        //20
        val descriptValue = "Adcdefaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        //80
        val contentValue = "Adcdefaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Adcdefaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ffffffff"
        val author = "Author author"

        val newsItem = NewsItemViewData(NewsItem("2019-09-09T23:28:00Z", titleValue, descriptValue,
            author, contentValue, "url", "url"), mockResources)
        newsItem.item.source = Source("abc", "abc")

        Assert.assertTrue(newsItem.posted.contains("hours ago"))
        Assert.assertTrue(newsItem.shortSource.contains("From"))
        Assert.assertTrue(newsItem.shortContent.length <= 80)

    }


}