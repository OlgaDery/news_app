package com.bonial.newsapp.di

import com.bonial.newsapp.MainActivity
import com.bonial.newsapp.retrofit.RequestNewsService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(restService: RequestNewsService)

    fun addComponent(viewModelModule: ViewModelModule): ViewModelSubComponent
}