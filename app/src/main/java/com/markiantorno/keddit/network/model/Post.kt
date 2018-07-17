package com.markiantorno.keddit.network.model

data class Post(
        val id: String,
        val author: String,
        val title: String,
        val num_comments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
)