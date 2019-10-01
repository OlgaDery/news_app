package com.bonial.newsapp

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext

class LifecycleManagedCoroutineScope(val lifecycleCoroutineScope: LifecycleCoroutineScope,
                                     override val coroutineContext: CoroutineContext
): ManagedCoroutineScope {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job = lifecycleCoroutineScope.launchWhenStarted(block)
}

class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {
    @ExperimentalCoroutinesApi
    val scope = TestCoroutineScope(coroutineContext)

    @ExperimentalCoroutinesApi
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch {
            block.invoke(this)
        }
    }
}