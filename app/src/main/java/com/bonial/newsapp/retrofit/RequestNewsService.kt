package com.bonial.newsapp.retrofit

import com.bonial.newsapp.database.TempUtils
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
    val country = "gr"
    val apiKey = "776308df36584d5fb2fc1fc2b9da6ce8"
    val pageSize = 21

    override suspend fun getRecentNews(): List<NewsItem>? {
        val request = suspendCoroutine<Response<List<NewsItem>>?> {
            val call: Call<List<NewsItem>> = retrofitService.getTopNews()
            call.enqueue(object : Callback<List<NewsItem>> {
                override fun onResponse(call: Call<List<NewsItem>>, response: Response<List<NewsItem>>) {
                    it.resume(response)
                }
                override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) {
                    t.printStackTrace()
                    it.resume(null)
                }
            })
        }
        return request!!.body()
        //val tempList = TempUtils.populateObjects(Date(), 21, true)

    }

    interface NewsApiCalls {

        @GET("TimeManagement")
        fun getTopNews(@Query("country") country: String = "gr", @Query("apiKey") apiKey: String = "776308df36584d5fb2fc1fc2b9da6ce8",
                       @Query("pageSize") pageSize: Int = 100): Call<List<NewsItem>>
    }


}