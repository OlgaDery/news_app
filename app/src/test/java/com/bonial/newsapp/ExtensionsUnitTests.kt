package com.bonial.newsapp

import android.content.Context
import org.junit.Before
import org.mockito.Mock
import android.net.NetworkInfo
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.bonial.newsapp.model.NewsItem
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test

class ExtensionsUnitTests {

    @Mock
    private lateinit var mockContext: Context

    val gson: Gson by lazy(LazyThreadSafetyMode.NONE) { Gson() }

    private val json = ""

    @Before
    fun setup () {
        val networkInfo = mock(NetworkInfo::class.java)
        val networkCapabilities = mock(NetworkCapabilities::class.java)

        `when`(networkInfo.isConnected).thenReturn(true)
        `when`(networkInfo.type).thenReturn(ConnectivityManager.TYPE_MOBILE)
        `when`(networkInfo.isRoaming).thenReturn(true)

        val connectivityManager = mock(ConnectivityManager::class.java)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        val context = mock(Context::class.java)
        `when`<Any>(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

    }

    @Test
    fun <T> string_deserializeJson() {
        val items = json.deserializeJsonToList<T>(NewsItem::class.java, gson)
        Assert.assertTrue(items!![0] is NewsItem)

    }

}