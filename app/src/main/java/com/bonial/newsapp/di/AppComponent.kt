package com.bonial.newsapp.di

import com.bonial.newsapp.MainActivity
import com.bonial.newsapp.database.DatabaseService
import com.bonial.newsapp.retrofit.RequestNewsService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(restService: RequestNewsService)

    fun inject(databaseService: DatabaseService)

    fun addComponent(viewModelModule: ViewModelModule): ViewModelSubComponent
}