package com.bonial.newsapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

fun View.resetLayoutParameters(height: Int, width: Int) {
    this.layoutParams.height = height
    this.layoutParams.width = width
}

fun Context.checkNetwork(): Int {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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


fun Context.getWidthInches(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return metrics.widthPixels
}

fun Context.getHightInches(): Int {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return metrics.heightPixels
}

fun Context.findScreenSize(): String {
    val metrics = DisplayMetrics()
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(metrics)
    val yInches = metrics.heightPixels / metrics.ydpi
    val xInches = metrics.widthPixels / metrics.xdpi
    val diagonalInches = Math.sqrt((xInches * xInches + yInches * yInches).toDouble())
    return if (diagonalInches >= 6.5) {
        // 6.5inch device or bigger
        "TABLET"
    } else {
        // smaller device
        "PHONE"
    }
}

fun Context.getOrientation(): String {
    val orientation: String
    val orientationValue = this.resources.configuration.orientation
    orientation = if (orientationValue == Configuration.ORIENTATION_PORTRAIT) {
        "PORTRAIT"
    } else {
        "LANDSCAPE"
    }
    return orientation
}

fun Context.showToast(message: String) {
    Toast.makeText(this,
        message,
        Toast.LENGTH_LONG).show()
}

fun String?.parseUrl(): String {
    return if (this != null && this.length > 5) {
        this.replace("\\", "")
    } else {
        "no"
    }
}

fun <T> String.deserializeJsonToList(type: Type, gson: Gson): List<T>? {
    val newToken = TypeToken.getParameterized(List::class.java, type).type
    return gson.fromJson<ArrayList<T>>(this, newToken)
}

fun Date?.extractYear(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}

fun Date.getDateBeforeOrAfter(gap: Int, before: Boolean): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    if (before) {
        calendar.set(this.extractYear(), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - gap)
    } else {
        calendar.set(this.extractYear(), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) + gap)
    }
    return calendar.time
}