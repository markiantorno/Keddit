package com.markiantorno.keddit.ui.listitems.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.markiantorno.keddit.ui.listitems.viewholder.LoadingViewHolder

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) { }
}