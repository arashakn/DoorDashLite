package com.doordash

import android.app.Application
import android.content.Context

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
    init {
        instance = this
    }
    companion object {
        private var instance: AppApplication? = null
        fun applicationContext(): Context {
            return instance!!
        }
    }
}