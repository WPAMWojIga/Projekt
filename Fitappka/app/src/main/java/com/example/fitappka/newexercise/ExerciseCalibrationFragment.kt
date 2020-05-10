package com.example.fitappka.newexercise

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentExerciseCalibrationBinding
import com.example.fitappka.databinding.FragmentExerciseNewBinding

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


        return binding.root
    }



}