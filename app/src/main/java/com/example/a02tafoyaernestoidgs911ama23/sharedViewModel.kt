package com.example.a02tafoyaernestoidgs911ama23

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var realTemp: MutableLiveData<Float> = MutableLiveData()
    private var realHum: MutableLiveData<Float> = MutableLiveData()
    public fun setRealTem(value: Float) {
        realTemp.value = value.toFloat()
    }

    public fun getRealTem(): Float? {
        return realTemp.value
    }
    public fun setRealHum(value: Float) {
        realHum.value = value.toFloat()
    }

    public fun getRealHum(): Float? {
        return realHum.value
    }

}