package com.bonial.newsapp

import android.content.Context
import android.content.res.Resources
import org.mockito.Mock

open class BaseUnitTest {

    @Mock
    lateinit var mockResources: Resources

    @Mock
    lateinit var context: Context
}