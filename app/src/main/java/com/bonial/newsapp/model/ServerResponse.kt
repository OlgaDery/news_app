package com.bonial.newsapp.model

data class ServerResponse(val status: String, val totalResults: Int, val articles: List<NewsItem>) {
}