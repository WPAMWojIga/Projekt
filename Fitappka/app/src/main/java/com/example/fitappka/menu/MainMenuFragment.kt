package com.example.fitappka.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.databinding.DataBindingUtil
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentMenuMainBinding

import androidx.navigation.findNavController


class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setContentView does not exist in fragment so we inflate
        val binding: FragmentMenuMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu_main, container, false
        )

        binding.newTraining.setOnClickListener { view: View ->
            view.findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragmentToNewTrainingFragment())
        }

        binding.newExercise.setOnClickListener { view: View ->
            view.findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragmentToNewExerciseFragment())
        }

        binding.doTraining.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(MainMenuFragmentDirections.actionMainMenuFragmentToTrainingSelectionFragment())
        }
        return binding.root
    }

}
