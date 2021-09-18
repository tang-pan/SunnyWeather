package com.yh.learningclan.sunnyweather.logic

import androidx.lifecycle.liveData
import com.yh.learningclan.sunnyweather.logic.dao.PlaceDao
import com.yh.learningclan.sunnyweather.logic.model.Place
import com.yh.learningclan.sunnyweather.logic.model.Weather
import com.yh.learningclan.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * Created by yuhong on 2021/9/15
 * 仓库层的统一封装入口
 */
object Repository {

    // 自动构建并返回一个LiveData对象 运行在子线程中
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        // 搜索城市数据
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    // 刷新天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        // 创建了一个协程作用域
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            // 保证只有在两个网络请求都成功响应之后，才会进一步执行程序
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                // 将Realtime和Daily对象取出并封装到一个Weather对象中
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                // 使用Result.success()方法来包装这个Weather对象
                Result.success(weather)
            } else {
                // 使用Result.failure()方法来包装一个异常信息
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    /**
     * liveData()函数的参数 接收标准定义的一个高阶函数，在fire()函数的内部会先调用liveData()函数，
     * 然后在liveData()函数的代码块中统一进行了try catch处理，并在try语句中调用传入的Lambda表达式中的代码，
     * 最终获取Lambda表达式的执行结果并调用emit()方法发射出去，声明一个suspend关键字，以表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            // 将包装的结果发射出去 类似于调用LiveData的setValue()方法来通知数据变化
            emit(result)
        }
}