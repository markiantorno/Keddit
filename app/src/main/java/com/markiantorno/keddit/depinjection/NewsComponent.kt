package com.markiantorno.keddit.depinjection

import com.markiantorno.keddit.ui.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NewsModule::class), (NetworkModule::class)])
interface NewsComponent {

    fun inject(newsFragment: NewsFragment)

}