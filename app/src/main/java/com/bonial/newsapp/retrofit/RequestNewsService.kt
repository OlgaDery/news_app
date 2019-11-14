package com.bonial.newsapp.retrofit

import com.bonial.newsapp.model.NewsItem
import com.bonial.newsapp.model.ServerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RequestNewsService @Inject constructor(): RequestNewsCalls {

    @Inject
    lateinit var retrofit: RetrofitProvider
    private val retrofitService by lazy {
        retrofit.getRetrofit().create(NewsApiCalls::class.java)
    }

    override suspend fun getRecentNews(): List<NewsItem>? {
        val request = suspendCoroutine<Response<ServerResponse>?> {
            val call: Call<ServerResponse> = retrofitService.getTopNews()
            call.enqueue(object : Callback<ServerResponse> {
                override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                    it.resume(response)
                }
                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    println(t.printStackTrace())
                    it.resume(null)
                }
            })
        }
        return request?.body()?.articles

    }

    interface NewsApiCalls {

        @GET("top-headlines")
        fun getTopNews(@Query("country") country: String = "de", @Query("apiKey") apiKey: String = "776308df36584d5fb2fc1fc2b9da6ce8",
                       @Query("pageSize") pageSize: Int = 100): Call<ServerResponse>
    }


}