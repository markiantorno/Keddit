package com.markiantorno.keddit.network.services.viewobjects

data class RedditNews(
        val after: String,
        val before: String,
        val news: List<RedditNewsItem>?)