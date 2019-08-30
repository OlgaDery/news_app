package com.bonial.newsapp.retrofit

import com.bonial.newsapp.database.TempUtils
import com.bonial.newsapp.getDateBeforeOrAfter
import com.bonial.newsapp.model.NewsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RequestNewsService @Inject constructor(): RequestNewsCalls {

    @Inject
    lateinit var retrofit: RetrofitProvider
    private val retrofitService by lazy {
        retrofit.getRetrofit().create(NewsApiCalls::class.java)
    }
    val path = "abc"
    val queryName = "abc"
    val queryValue = "abc"

    override suspend fun getRecentNews(date: Date): List<NewsItem>? {
//        val request = suspendCoroutine<Response<List<NewsItem>>?> {
//            val call: Call<List<NewsItem>> = retrofitService.getScheduleOverview(queryValue)
//            call.enqueue(object : Callback<List<NewsItem>> {
//                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
//                    it.resume(response)
//                }
//                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
//                    t.printStackTrace()
//                    it.resume(null)
//                }
//            })
//        }
        //return request!!.body()
        val tempList = TempUtils.populateObjects(date, 21, true)

        return tempList
    }

    interface NewsApiCalls {

        @GET("TimeManagement")
        fun getScheduleOverview(@Query("templateId") templateId: String): Call<List<NewsItem>>
    }


}