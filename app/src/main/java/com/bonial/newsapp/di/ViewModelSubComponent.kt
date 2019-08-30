package com.bonial.newsapp.di

import com.bonial.newsapp.NewsFeedViewModel
import dagger.Subcomponent

@Subcomponent(modules = [(ViewModelModule::class)])
interface ViewModelSubComponent {

    fun inject(viewModel: NewsFeedViewModel)
}