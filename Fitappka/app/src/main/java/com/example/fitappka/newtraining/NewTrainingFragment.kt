package com.example.fitappka.newtraining

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

// DataBinding
import com.example.fitappka.R
import androidx.databinding.DataBindingUtil
import com.example.fitappka.databinding.FragmentTrainingNewBinding

// Navigation
import androidx.navigation.findNavController

// ViewModel Architecture
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer

class NewTrainingFragment : Fragment() {

    private lateinit var viewModel: NewTrainingViewModel

    private lateinit var exerciseList: ArrayList<String>
    private lateinit var exerciseAdapter: ArrayAdapter<String>

    private lateinit var exerciseText: EditText
    private lateinit var addButton: Button
    private lateinit var lv: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTrainingNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_training_new, container, false)

        // ViewModel
        viewModel = ViewModelProviders.of(this).get(NewTrainingViewModel::class.java)

        // Spinner
        val spinner: Spinner = binding.trainingType
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.training_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Navigation
        binding.addExerciseButton.setOnClickListener {view: View ->
            view.findNavController().navigate(NewTrainingFragmentDirections.actionNewTrainingFragmentToExerciseSpecificationFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        var args = NewTrainingFragmentArgs.fromBundle(arguments!!)
        if (args.exerciseName != ""){
            viewModel.addExercise(args.exerciseName)
        }
    }

}
