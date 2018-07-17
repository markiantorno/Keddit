package com.markiantorno.keddit.network.services

import com.markiantorno.keddit.network.model.RedditNewsResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("/top.json")
    fun getTop(@Query("after") after: String, @Query("limit") limit: String): Single<RedditNewsResponse>

}