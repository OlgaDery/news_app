package com.bonial.newsapp.model

import java.util.*

data class NewsItem (val id: String, val date: Date, val name: String, val derscription: String, val imageUrl: String? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItem

        if (id != other.id) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}