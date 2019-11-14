package com.bonial.newsapp

import junit.framework.Assert.*
import org.junit.Test

class DateExtensionsUnitTest {

    @Test
    fun testDateExtensions () {
        val patterns = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "M/dd/yyyy h:mm a"
        )
        val sampleData = arrayOf(
            "2016-06-25T00:00:00",
            "2016-06-25T00:00:00.345679",
            "07/08/2018 2:48 PM"
        )
        var count = 0
        patterns.toList().forEach {
            try {
                sampleData[count].toDate(it)
            } catch (e: Exception) {
                fail()
            }
            count++
        }
    }
}