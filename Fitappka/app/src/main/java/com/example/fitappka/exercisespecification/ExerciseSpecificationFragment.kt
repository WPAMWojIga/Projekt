package com.example.fitappka.exercisespecification

import android.content.Context
import android.os.Bundle
import android.util.Log

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.fragment.app.Fragment

import androidx.navigation.findNavController

import com.example.fitappka.R
import androidx.databinding.DataBindingUtil
import com.example.fitappka.databinding.FragmentSpecificationExerciseBinding

// Close keyboard
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager

class ExerciseSpecificationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSpecificationExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_specification_exercise, container, false
        )

        binding.button.setOnClickListener {view: View ->
            // TextEdit never returns null, at least it returns empty String - ""
            var text: String = binding.editText.text.toString()
            if (text != ""){
                Log.i("ExerciseSpecFragment", text)//Hide the keyboard.
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.findNavController().navigate(ExerciseSpecificationFragmentDirections.actionExerciseSpecificationFragmentToNewTrainingFragment(text))
            }
        }

        return binding.root
    }
}