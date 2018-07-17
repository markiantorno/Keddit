package com.markiantorno.keddit.ui.listitems.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.markiantorno.keddit.network.model.ChildPostObject
import com.markiantorno.keddit.network.model.RedditData
import com.markiantorno.keddit.network.services.RedditService
import io.reactivex.disposables.CompositeDisposable

class RedditDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                              private val redditService: RedditService)
    : DataSource.Factory<String, ChildPostObject>() {

    val redditDataSourceLiveData = MutableLiveData<RedditDataSource>()

    override fun create(): DataSource<String, ChildPostObject> {
        val redditDataSource = RedditDataSource(redditService, compositeDisposable)
        redditDataSourceLiveData.postValue(redditDataSource)
        return redditDataSource
    }
}