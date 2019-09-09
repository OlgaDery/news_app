package com.bonial.newsapp

import android.content.res.Resources
import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.NewsItemViewData
import com.bonial.newsapp.model.Source
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class NewsItemViewDataTest {

    @Mock
    private lateinit var mockResources: Resources

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

        val newsItem = NewsItemViewData(NewsItem(Date(), titleValue, descriptValue,
            author, contentValue, "url", "url", Source("", "Adbc chnh")), mockResources)

        Assert.assertTrue(newsItem.posted.contains("hours ago"))
        Assert.assertTrue(newsItem.shortSource.contains("From"))
        Assert.assertTrue(newsItem.shortContent.length <= 80)

    }


}