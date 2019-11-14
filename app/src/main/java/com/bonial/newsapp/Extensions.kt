package com.bonial.newsapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
fun Context.checkNetwork(): Int {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return 0 //No
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return 1// WIFI
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return 2 //Cell
        }
    } else {
        if (connectivityManager.activeNetworkInfo == null) {
            return 0
        }
        if (connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return 1

        }
        if (connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return 2
        }
    }
    return 0
}

fun Context.getOrientation(): String {
    return if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        NewsFeedViewModel.PORTRAIT_MODE
    } else {
        NewsFeedViewModel.LANDSCAPE_MODE
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Date.getDateBeforeOrAfter(gap: Int, before: Boolean): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    if (before) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - gap)
    } else {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) + gap)
    }
    return calendar.time
}

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, Locale.GERMANY)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.parse(this)
}