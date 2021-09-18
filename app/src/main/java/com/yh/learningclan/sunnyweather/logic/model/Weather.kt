package com.yh.learningclan.sunnyweather.logic.model

/**
 * Created by yuhong on 2021/9/16
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)