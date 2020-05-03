package com.example.fitappka.newtraining

// Debug purposes
import android.util.Log

// ViewModel structure
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.fitappka.database.ExerciseListRepository

class NewTrainingViewModel: ViewModel(){

    private val exercisesListRepository: ExerciseListRepository = ExerciseListRepository

    private val _exercisesList = MutableLiveData<List<String>>()
    val exercisesList: LiveData<List<String>>
        get() = _exercisesList

    fun addExercise(exerciseName: String){
        _exercisesList.value = exercisesListRepository.add(exerciseName)
    }

    fun removeExercise(exerciseName: String){
        _exercisesList.value = exercisesListRepository.remove(exerciseName)
        refreshExercises() // Necessary? Not sure
    }

    fun onResume() {
        refreshExercises()
    }

    fun refreshExercises(){
        _exercisesList.value = exercisesListRepository.fetchAll()
    }



}