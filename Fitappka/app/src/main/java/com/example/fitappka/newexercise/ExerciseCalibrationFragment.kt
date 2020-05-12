package com.example.fitappka.newexercise

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.fitappka.R
import com.example.fitappka.database.Exercise
import com.example.fitappka.database.FitappkaDatabase
import com.example.fitappka.databinding.FragmentExerciseCalibrationBinding
import com.example.fitappka.databinding.FragmentExerciseNewBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class ExerciseCalibrationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentExerciseCalibrationBinding = DataBindingUtil.inflate<FragmentExerciseCalibrationBinding >(
            inflater, R.layout.fragment_exercise_calibration, container, false
        )

        val args = ExerciseCalibrationFragmentArgs.fromBundle(requireArguments())
        binding.calibrationExDetails.run{setText(args.exerciseData[0]+"\n"+args.exerciseData[1])}

        binding.calibrationScanButton.setOnClickListener {
            lateinit var exercises : List<Exercise>
            var names = ""
                val database = FitappkaDatabase.getInstance(requireActivity().applicationContext)
                exercises = database.fitappkaDatabaseDao.getAllExercises()

            if (!exercises.isEmpty()) {
                for (i in 0..exercises.lastIndex) {
                    names = names + "\n" + exercises[i].exerciseId.toString() + "  " +exercises[i].exerciseName
                }
                binding.calibrationExDetails.run { text = names}
            }
            lateinit var exercise :Exercise
            exercise = Exercise(
                0, args.exerciseData[0],
                args.exerciseData[1],
                args.exerciseData[2],
                args.exerciseData[3]
            )
            database.fitappkaDatabaseDao.insertExercise(exercise)
            view?.let { it1 -> Snackbar.make(it1,"Pomyślnie dodano ćwiczenie :)", Snackbar.LENGTH_LONG).show() }
            database.close()
            view?.findNavController()?.navigate(ExerciseCalibrationFragmentDirections.actionExerciseCalibrationFragmentToMainMenuFragment())

        }


        return binding.root
    }



}