package com.markiantorno.keddit.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.markiantorno.keddit.network.model.ChildPostObject
import com.markiantorno.keddit.network.services.RedditService
import com.markiantorno.keddit.ui.listitems.paging.RedditDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PostViewModel: ViewModel() {

    var postList: LiveData<PagedList<ChildPostObject>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 10

    private val sourceFactory: RedditDataSourceFactory

    init {
        var service = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditService::class.java)
        sourceFactory = RedditDataSourceFactory(compositeDisposable, service)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        postList = LivePagedListBuilder<String, ChildPostObject>(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}