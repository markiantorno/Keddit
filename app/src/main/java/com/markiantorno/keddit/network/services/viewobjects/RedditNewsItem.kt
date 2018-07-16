package com.markiantorno.keddit.network.services.viewobjects

import com.markiantorno.keddit.ui.listitems.adapters.AdapterConstants
import com.markiantorno.keddit.ui.listitems.adapters.ViewType

data class RedditNewsItem(
        val author: String,
        val title: String,
        val numComments: Int,
        val created: Long,
        val thumbnail: String,
        val url: String
) : ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}