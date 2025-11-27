package com.suman.custom_viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel: ViewModel() {

    private val mCounterFlow = MutableStateFlow(0)
    val counterStateFlow :  StateFlow<Int>
        get() = mCounterFlow
    fun increaseCounter(){
        mCounterFlow.value = mCounterFlow.value.plus(1)
    }
}