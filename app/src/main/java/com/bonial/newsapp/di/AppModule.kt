package com.bonial.newsapp.di

import android.content.Context
import androidx.annotation.NonNull
import com.bonial.newsapp.database.DatabaseProvider
import com.bonial.newsapp.database.DatabaseService
import com.bonial.newsapp.retrofit.RetrofitProvider
import com.bonial.newsapp.retrofit.RetrofitService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (@NonNull val context: Context) {

    @Provides
    @Singleton
    fun accessContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun accessDatabaseService(): DatabaseProvider {
        return DatabaseService(context)
    }

    @Provides
    @Singleton
    fun accessRetrofitFactory(): RetrofitProvider {
        return RetrofitService()
    }

    @Provides
    @Singleton
    fun accessGson(): Gson {
        return Gson()
    }
}
