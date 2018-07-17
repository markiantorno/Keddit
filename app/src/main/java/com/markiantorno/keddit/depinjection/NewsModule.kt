package com.markiantorno.keddit.depinjection

import com.markiantorno.keddit.network.services.NewsAPI
import com.markiantorno.keddit.network.services.NewsRestAPI
import com.markiantorno.keddit.network.services.RedditService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NewsModule {

    @Provides
    @Singleton
    fun provideNewsAPI(redditApi: RedditService): NewsAPI {
        return NewsRestAPI(redditApi)
    }

    @Provides
    @Singleton
    fun provideRedditApi(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }

    /**
     * NewsManager is automatically provided by Dagger as we set the @Inject annotation in the
     * constructor, so we can avoid adding a 'provider method' here.
     */
}