package com.yh.learningclan.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.yh.learningclan.sunnyweather.SunnyWeatherApplication
import com.yh.learningclan.sunnyweather.logic.model.Place

/**
 * Created by yuhong on 2021/9/17
 */
object PlaceDao {

    // Place对象存储到SharedPreferences文件
    fun savePlace(place: Place) {
        sharePreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavePlace(): Place {
        val placeJson = sharePreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    // 判断是否有数据已被存储
    fun isPlaceSaved() = sharePreferences().contains("place")

    private fun sharePreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}