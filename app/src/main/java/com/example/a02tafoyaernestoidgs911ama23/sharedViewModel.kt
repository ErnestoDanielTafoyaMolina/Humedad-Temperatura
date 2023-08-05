package com.example.a02tafoyaernestoidgs911ama23

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.util.Date

class SharedViewModel : ViewModel() {
    private val TAG = "SharedViewModel"
    private val mTempReal = MutableLiveData<Int>().apply { value=0 }
    private val mHumReal = MutableLiveData<Int>().apply { value=0 }
    private var currentDateTime=MutableLiveData<String>().apply{value=
        DateFormat.getDateTimeInstance().format(Date())}
    private val mDataInPrint = MutableLiveData<String>().apply { value="" }
    fun getmDataInPrint(): LiveData<String?>? {
        return getmDataInPrint()
    }
    fun setmDataInPrint(dataInPrint: String) {
        mDataInPrint.value = dataInPrint
    }
    fun getTempReal(): LiveData<Int?> {
        return mTempReal
    }
    fun setTempReal(tempReal: Int) {
        mTempReal.value = tempReal
    }
    fun setCurrentDayTime(currentDayTime: String) {
        currentDateTime.value = currentDayTime
    }
    fun getHumReal(): LiveData<Int?> {
        return mHumReal
    }
    fun setHumReal(tempReal: Int) {
        mHumReal.value = tempReal
    }
    fun getCurrentDayTime(): LiveData<String?> {
        return currentDateTime
    }
}