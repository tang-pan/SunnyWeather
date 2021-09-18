package com.yh.learningclan.sunnyweather.logic.network

import com.yh.learningclan.sunnyweather.SunnyWeatherApplication
import com.yh.learningclan.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by yuhong on 2021/9/15
 */
interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}