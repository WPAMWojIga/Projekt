package com.example.fitappka.newexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import com.example.fitappka.databinding.FragmentExerciseNewBinding
import com.example.fitappka.R

class NewExerciseFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentExerciseNewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exercise_new, container, false
        )

        var args = NewExerciseFragmentArgs.fromBundle(arguments!!)

        binding.cos.text = args.zmienna

        return binding.root
    }
}