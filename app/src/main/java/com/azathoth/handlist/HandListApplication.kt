package com.azathoth.handlist

import android.app.Application
import com.azathoth.handlist.data.AppContainer
import com.azathoth.handlist.data.AppDataContainer

class HandListApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}