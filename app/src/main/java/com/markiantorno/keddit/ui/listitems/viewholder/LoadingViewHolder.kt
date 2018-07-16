package com.markiantorno.keddit.ui.listitems.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.markiantorno.keddit.R
import com.markiantorno.keddit.extensions.getFriendlyTime
import com.markiantorno.keddit.extensions.inflate
import com.markiantorno.keddit.extensions.loadImg
import com.markiantorno.keddit.network.services.viewobjects.RedditNewsItem
import kotlinx.android.synthetic.main.news_item.view.*

class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view)  {

    companion object {
        fun create(parent: ViewGroup): LoadingViewHolder {
            val view = parent.inflate(R.layout.news_item_loading)
            return LoadingViewHolder(view)
        }
    }
}