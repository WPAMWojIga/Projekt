package com.example.fitappka.traininginprogress

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitappka.database.*

class TrainingProgressViewModel() : ViewModel() {

    private val fitappkaRepository = FitappkaRepository
    var currentExercise = 0

    private var _availableTrainings = MutableLiveData<List<Training>> ()
    val availableTrainings : LiveData<List<Training>>
        get() = _availableTrainings

    private var _selectedTrainingPosition = -1
    val selectedTrainingPosition : Int
        get() = _selectedTrainingPosition

    private lateinit var _exercises : List<Exercise>
    val exercises : List<Exercise>
        get() = _exercises

    private  var _trainingExercisesCrossRefs: List<TrainingExerciseCrossRef> = listOf()
    val trainingExerciseCrossRef : List<TrainingExerciseCrossRef>
        get() = _trainingExercisesCrossRefs

    private var _trainingsWithExercises = MutableLiveData<List<FitappkaDatabaseDao.TrainingWithExercises>>()
    val trainingsWithExercises : LiveData<List<FitappkaDatabaseDao.TrainingWithExercises>>
        get() = _trainingsWithExercises

    fun refreshTrainingsWithExercises() {
        _trainingsWithExercises.postValue(fitappkaRepository.getTrainingsWithExercises())
    }

    fun refreshTrainings() {
        _availableTrainings.value = fitappkaRepository.getAllTrainings()
    }

    fun setSelectedTrainingPosition(position : Int) {
        _selectedTrainingPosition = position
    }

    fun refreshCrossRefs() {
        if (_selectedTrainingPosition != -1) {
            _trainingExercisesCrossRefs = fitappkaRepository.getCrossRefsInTraining(
                _trainingsWithExercises.value!![selectedTrainingPosition].training.trainingId)

        }
    }


    fun trainingFinished() : Boolean =
        currentExercise > _trainingsWithExercises.value!![selectedTrainingPosition].exercises.lastIndex

    fun onResume() {
        //refreshTrainings()
        refreshTrainingsWithExercises()
    }



}