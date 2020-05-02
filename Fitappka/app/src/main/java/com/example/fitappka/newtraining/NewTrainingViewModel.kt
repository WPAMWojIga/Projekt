package com.example.fitappka.newtraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewTrainingViewModel: ViewModel(){

    private val _exercisesList = MutableLiveData<List<String>>()
    val exercisesList: LiveData<List<String>>
        get() = _exercisesList

    fun addExercise(exerciseName: String){
        //_exercisesList
        //TODO: Adding items, then add recycleview and observer.
    }



}