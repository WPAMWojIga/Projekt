package com.example.fitappka.exercisespecification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentSpecificationExerciseBinding

class ExerciseSpecificationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSpecificationExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_specification_exercise, container, false
        )

        binding.button.setOnClickListener {view: View ->
            var text: String? = binding.editText.text.toString()
            if (text != ""){
                Log.i("ExerciseSpecFragment", text)
                view.findNavController().navigate(ExerciseSpecificationFragmentDirections.actionExerciseSpecificationFragmentToNewTrainingFragment(text))
            }
        }

        return binding.root
    }
}