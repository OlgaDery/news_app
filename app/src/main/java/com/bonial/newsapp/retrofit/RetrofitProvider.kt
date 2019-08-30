package com.bonial.newsapp.retrofit

import retrofit2.Retrofit

interface RetrofitProvider {

    fun getRetrofit(url: String = "http://newsapi.org"): Retrofit
}