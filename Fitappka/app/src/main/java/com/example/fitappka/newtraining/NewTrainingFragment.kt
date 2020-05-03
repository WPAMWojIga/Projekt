package com.example.fitappka.newtraining

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_training_new.*

class NewTrainingFragment : Fragment() {

    private lateinit var viewModel: NewTrainingViewModel
    private val exerciseListRecycleViewAdapter: ExerciseListRecycleViewAdapter = ExerciseListRecycleViewAdapter(listOf(), { viewModel.removeExercise(it) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTrainingNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_training_new, container, false)

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // ViewModel
        viewModel = ViewModelProviders.of(this).get(NewTrainingViewModel::class.java)

        // RecycleView Observer
        viewModel.exercisesList.observe(this, Observer {
            exerciseListRecycleViewAdapter.apply{
                exerciseList = it
                notifyDataSetChanged()
            }
        })

        exercise_list_recycle_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = exerciseListRecycleViewAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        var args = NewTrainingFragmentArgs.fromBundle(arguments!!)
        // NPE handler
        Log.i("NewTrainingFrag", args?.exerciseName ?: "No arguments")
        if (args?.exerciseName != "" && args?.exerciseName != null){
            viewModel.addExercise(args.exerciseName.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

}
