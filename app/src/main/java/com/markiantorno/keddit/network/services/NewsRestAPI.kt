package com.markiantorno.keddit.network.services

import com.markiantorno.keddit.network.model.RedditNewsResponse
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

class NewsRestAPI @Inject constructor(private val redditApi: RedditService) : NewsAPI {

    override fun getNews(after: String, limit: String): Single<RedditNewsResponse> {
        return redditApi.getTop(after, limit)
    }
}