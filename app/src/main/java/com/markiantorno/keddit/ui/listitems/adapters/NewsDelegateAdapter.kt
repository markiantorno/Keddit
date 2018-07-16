package com.markiantorno.keddit.ui.listitems.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.markiantorno.keddit.network.services.viewobjects.RedditNewsItem
import com.markiantorno.keddit.ui.listitems.viewholder.PostViewHolder

class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as PostViewHolder
        holder.bind(item as RedditNewsItem)
    }
}