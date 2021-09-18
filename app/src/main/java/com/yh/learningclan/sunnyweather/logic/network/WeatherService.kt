package com.yh.learningclan.sunnyweather.logic.network

import com.yh.learningclan.sunnyweather.SunnyWeatherApplication
import com.yh.learningclan.sunnyweather.logic.model.DailyResponse
import com.yh.learningclan.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yuhong on 2021/9/16
 */
interface WeatherService {

    // 获取实时的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    // 获取未来的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}