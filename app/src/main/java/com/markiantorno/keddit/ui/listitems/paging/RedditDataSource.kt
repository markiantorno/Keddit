package com.markiantorno.keddit.ui.listitems.paging

import android.arch.paging.ItemKeyedDataSource
import com.markiantorno.keddit.network.model.ChildPostObject
import com.markiantorno.keddit.network.model.RedditData
import com.markiantorno.keddit.network.services.RedditService
import io.reactivex.disposables.CompositeDisposable

class RedditDataSource(
        private val redditService: RedditService,
        private val compositeDisposable: CompositeDisposable)
    : ItemKeyedDataSource<String, ChildPostObject>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ChildPostObject>) {
        compositeDisposable.add(redditService.getTop("","10").subscribe ({ t ->
            callback.onResult(t?.data?.children!!)
        }, {
            //TODO
        }))
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ChildPostObject>) {
        compositeDisposable.add(redditService.getTop(params.key,"10").subscribe ({ t ->
            callback.onResult(t?.data?.children!!)
        }, {
            //TODO
        }))
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ChildPostObject>) {
        compositeDisposable.add(redditService.getTop(params.key,"10").subscribe ({ t ->
            callback.onResult(t?.data?.children!!)
        }, {
            //TODO
        }))
    }

    override fun getKey(item: ChildPostObject): String {
        return item.data.id
    }
}