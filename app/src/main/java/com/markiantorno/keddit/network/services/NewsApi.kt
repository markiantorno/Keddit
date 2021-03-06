package com.markiantorno.keddit.network.services

import com.markiantorno.keddit.network.model.RedditNewsResponse
import retrofit2.Call

interface NewsAPI {
    fun getNews(after: String, limit: String): Call<RedditNewsResponse>
}