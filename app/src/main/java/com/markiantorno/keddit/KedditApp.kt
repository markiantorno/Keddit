package com.markiantorno.keddit

import android.app.Application
import com.markiantorno.keddit.depinjection.AppModule
import com.markiantorno.keddit.depinjection.DaggerNewsComponent
import com.markiantorno.keddit.depinjection.NewsComponent

class KedditApp : Application() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}