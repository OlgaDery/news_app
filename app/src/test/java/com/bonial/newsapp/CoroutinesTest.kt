package com.bonial.newsapp

import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.retrofit.RequestNewsService
import com.bonial.newsapp.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CoroutinesTest {

    @ExperimentalCoroutinesApi
    private var testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private var managedCoroutineScope = TestScope(testDispatcher)

    private lateinit var viewModel: TestPresenter

    @ExperimentalCoroutinesApi
    @Before
    fun setup () {
        Dispatchers.setMain(testDispatcher)
        val apisMethods = RequestNewsService()
        apisMethods.retrofit = RetrofitService()
        viewModel = TestPresenter(managedCoroutineScope, apisMethods)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDataFromWebServer_ReceiveData() {
        val list = mutableListOf<NewsItem>()
        list.add(NewsItem(Date().toString(), "123", "", "", "", "", ""))
        list.add(NewsItem(Date().toString(), "1231", "", "", "", "", ""))
        list.add(NewsItem(Date().toString(), "1232", "", "", "", "", ""))

        val result = runBlocking {
            viewModel.apiMethods.getRecentNews()
            //viewModel.getDataFromWebServer(true)
        }
        Assert.assertTrue(!result.isNullOrEmpty())
    }
}