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

class PostViewHolder(view: View): RecyclerView.ViewHolder(view)  {

    fun bind(item: RedditNewsItem) = with(itemView) {
        img_thumbnail.loadImg(item.thumbnail)
        description.text = item.title
        author.text = item.author
        comments.text = "${item.numComments} comments"
        time.text = item.created.getFriendlyTime()
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val view = parent.inflate(R.layout.news_item)
            return PostViewHolder(view)
        }
    }
}