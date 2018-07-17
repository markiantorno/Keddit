package com.markiantorno.keddit.network.model

data class RedditData(
        val modhash: String,
        val children: List<ChildPostObject>,
        val after: String?,
        val before: String?
)
