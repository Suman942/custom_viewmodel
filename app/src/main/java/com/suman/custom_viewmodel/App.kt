package com.suman.custom_viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel

class App : Application() {

    val mHashMap = HashMap<String, ViewModel>()

    fun getViewModel(key: String): ViewModel{
        if (mHashMap[key] != null){
            return mHashMap[key]!!
        }
        val myViewModel = MyViewModel()
        mHashMap[key] = myViewModel
        return myViewModel
    }

    fun clearViewModel(key: String){
        mHashMap.remove(key)
    }
}