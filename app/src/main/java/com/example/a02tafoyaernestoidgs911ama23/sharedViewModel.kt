package com.example.a02tafoyaernestoidgs911ama23

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var mRealTemp: MutableLiveData<Int> = MutableLiveData()
    private var mRealHum: MutableLiveData<Int> = MutableLiveData()
    public fun setRealTem(value: Int) {
        mRealTemp.value = value
    }

    public fun getRealTem(): LiveData<Int?> {
        return mRealTemp
    }
    public fun setRealHum(value: Int) {
        mRealHum.value = value
    }

    public fun getRealHum(): LiveData<Int?> {
        return mRealHum
    }

}