package com.bonial.newsapp.di

import com.bonial.newsapp.retrofit.RequestNewsCalls
import com.bonial.newsapp.retrofit.RequestNewsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun accessRetrofitCalls(): RequestNewsCalls {
        return RequestNewsService()
    }
}