package com.yh.learningclan.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yh.learningclan.sunnyweather.logic.Repository
import com.yh.learningclan.sunnyweather.logic.model.Place

/**
 * Created by yuhong on 2021/9/15
 * ViewModel相当于逻辑层和UI层之间的一个桥梁，更偏向于逻辑层的部分
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    // 界面上显示的城市数 据进行缓存
    val placeList = ArrayList<Place>()

    // 使用Transformations的switchMap()方法来观察这个对象 当searchPlaces()函数被调用时，switchMap()方法所对应的转换函数就会执行
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        // 返回的LiveData对象转换成一个可供Activity观察的LiveData对象
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}