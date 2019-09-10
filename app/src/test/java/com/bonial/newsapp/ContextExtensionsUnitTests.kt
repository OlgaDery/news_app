package com.bonial.newsapp

import android.content.Context
import org.junit.Before
import org.mockito.Mock
import android.net.NetworkInfo
import org.mockito.Mockito.`when`
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@RunWith(MockitoJUnitRunner::class)
class ContextExtensionsUnitTests: BaseUnitTest() {

    @Mock
    private lateinit var networkInfo: NetworkInfo

    @Mock
    private lateinit var network: Network

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setup () {
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    }

    @Test
    fun test_context_checkNetwork_wifiOn() {
       checkReturnValueIfWifiOn()
    }

    @Test
    fun test_context_checkNetwork_cellularOn() {
        checkReturnValueIfMobileNetworkOn()
    }

    @Test
    fun test_context_checkNetwork_noNetwork() {
        checkReturnValueNoNetwork()
    }

    private fun checkReturnValueIfWifiOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
            `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)
        } else {
            `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
            `when`(connectivityManager.activeNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_WIFI)
        }
        Assert.assertTrue(context.checkNetwork() == 1)
    }

    private fun checkReturnValueIfMobileNetworkOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
            `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(true)

        } else {
            `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
            `when`(connectivityManager.activeNetworkInfo.type).thenReturn(ConnectivityManager.TYPE_MOBILE)
        }
        Assert.assertTrue(context.checkNetwork() == 2)
    }

    private fun checkReturnValueNoNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(null)
        } else {
            `when`(connectivityManager.activeNetworkInfo).thenReturn(null)
        }
        Assert.assertTrue(context.checkNetwork() == 0)
    }

}