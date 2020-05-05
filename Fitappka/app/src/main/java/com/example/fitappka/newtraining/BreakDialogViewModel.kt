package com.example.fitappka.newtraining

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BreakDialogViewModel: ViewModel(){
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int>
        get() = _counter

    val settingDoneFlag = MutableLiveData<Boolean>()

    init {
        _counter.value = 0
        settingDoneFlag.value = false
    }

    fun increment(){
        _counter.value = _counter.value?.plus(1)
        Log.i("BDF", "inc")
    }

    fun decrement(){
        val sth = _counter.value?.compareTo(0) ?: 0
        if(sth > 0) {
            _counter.value = _counter.value?.minus(1)
            Log.i("BDF", "dec")
        }
    }
}