package com.yh.learningclan.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by yuhong on 2021/9/15
 * 全局获取Context
 */
class SunnyWeatherApplication : Application() {

    companion object {

        const val TOKEN = "sKr3J6l7gPdkBptR"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}