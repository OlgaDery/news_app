package com.bonial.newsapp.model

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.bonial.newsapp.NewsFeedViewModel
import com.bonial.newsapp.R
import com.bonial.newsapp.toDate
import java.util.*
import java.util.concurrent.TimeUnit

@Entity(tableName = "table")
data class NewsItem (@ColumnInfo(name = "publishedAt")val publishedAt: String,
                              @PrimaryKey val title: String,
                              @ColumnInfo(name = "description") val description: String?,
                              @ColumnInfo(name = "urlToImage") val urlToImage: String?,
                              @ColumnInfo(name = "author") val author: String?,
                              @ColumnInfo(name = "content") val content: String?,
                              @ColumnInfo(name = "url") val url: String?)

{
    @Ignore var source: Source? = null
    @ColumnInfo(name = "sourceID")  var sourceID: String? = ""
        get() {
            field = source?.id
            return field
        }

    @ColumnInfo(name = "sourceName") var sourceName: String? = source?.name
        get() {
            field = source?.name
            return field
        }
}

data class NewsItemViewData (val item: NewsItem, val resources: Resources) {

    val shortDescription: String
        get() {
            if (item.description == null) {
                return ""
            }
            return if (item.description.length < 17) {
                item.description
            } else {
                item.description.substring(0, 13).plus("...")
            }
        }

    val shortTitle: String
        get() {
            return if (item.title.length < 17) {
                item.title
            } else {
                item.title.substring(0, 13).plus("...")
            }
        }

    val shortContent: String
        get() {
            if (item.content == null) {
                return ""
            }
            return if (item.content.length <= 80) {
                item.content
            } else {
                item.content.substring(0, 77).plus("...")
            }
        }

    val shortSource: String
        get() {
            if (item.source == null || item.source?.name == null) {
                return ""
            }
            return if (item.source!!.name!!.length <= 10) {
                resources.getString(R.string.from).plus(item.source!!.name)
            } else {
                resources.getString(R.string.from).plus(item.source!!.name!!.substring(0, 7).plus("..."))
            }
        }

    val posted: String
        get() {
            val millisec = Date().time - item.publishedAt.toDate(NewsFeedViewModel.FORMATTER).time
            return TimeUnit.MILLISECONDS.toHours(millisec).toString().substringBefore(".").
                plus(" ").plus(resources.getString(R.string.hours_ago))
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NewsItemViewData
        if (item.title != other.item.title) return false
        return true
    }

    override fun hashCode(): Int {
        var result = item.title.hashCode()
        result = 31 * result + item.publishedAt.hashCode()
        return result
    }
}