package com.example.numerosromanosconversor.view

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}