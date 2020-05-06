package com.example.fitappka.database

import androidx.room.*

@Dao
interface FitappkaDatabaseDao {
    @Transaction
    @Query("SELECT * FROM training_table")
    fun getTrainingsWithExercises(): List<TrainingWithExercises>
}