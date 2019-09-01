package com.bonial.newsapp.model

import java.util.*

data class NewsItem (val publishedAt: Date, val title: String, val description: String,
                     val urlToImage: String? = null, val author: String, val content: String, val url: String) {

    //TODO
    val posted: String
    get() {
        return (Date().time - publishedAt.time).toString().plus(" hours")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NewsItem
        if (title != other.title) return false
        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + publishedAt.hashCode()
        return result
    }
}