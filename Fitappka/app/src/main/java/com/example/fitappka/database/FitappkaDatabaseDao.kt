package com.example.fitappka.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FitappkaDatabaseDao {

    @Insert
    fun insertExercise(exercise: Exercise)
    @Update
    fun updateExercise(exercise: Exercise)
    @Query("SELECT * FROM exercise_table WHERE exerciseId = :key")
    fun getExercise(key: Long): Exercise
    @Delete
    fun deleteExercise(exercise: Exercise)
    @Query("SELECT * FROM exercise_table ORDER BY exerciseId DESC")
    fun getAllExercises(): LiveData<List<Exercise>> // Not sure if LiveData in our case is proper

    @Insert
    fun insertTraining(training: Training)
    @Update
    fun updateTraining(training: Training)
    @Query("SELECT * FROM training_table WHERE trainingId = :key")
    fun getTraining(key: Long): Training
    @Delete
    fun deleteTraining(training: Training)
    @Query("SELECT * FROM training_table ORDER BY trainingId DESC")
    fun getAllTrainings(): LiveData<List<Training>> // Not sure if LiveData in our case is proper

    @Insert
    fun insertTrainingExerciseCrossRef(trainingExerciseCrossRef: TrainingExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM training_table")
    fun getTrainingsWithExercises(): List<TrainingWithExercises>

}