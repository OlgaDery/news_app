package com.bonial.newsapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface ManagedCoroutineScope: CoroutineScope {

    fun launch(block: suspend CoroutineScope.() -> Unit) : Job
}