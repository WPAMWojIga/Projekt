package com.example.fitappka.newtraining

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
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

// Log & Debugging purposes
import android.util.Log
import com.example.fitappka.database.Exercise
import com.example.fitappka.database.FitappkaDatabase

class NewTrainingFragment: Fragment() {

    private lateinit var newTrainingViewModel: NewTrainingViewModel
    private val exerciseListRecycleViewAdapter: ExerciseListRecycleViewAdapter = ExerciseListRecycleViewAdapter(listOf(), { newTrainingViewModel.removeExercise(it) })

    private val breakAlertDialogFragment: BreakDialogFragment = BreakDialogFragment()
    private lateinit var breakDialogViewModel: BreakDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentTrainingNewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_training_new, container, false)

        // ViewModels
        newTrainingViewModel = ViewModelProviders.of(requireActivity()).get(NewTrainingViewModel::class.java)
        breakDialogViewModel = ViewModelProviders.of(activity!!).get(BreakDialogViewModel::class.java)

        //Exercises from Database
        val database = FitappkaDatabase.getInstance(requireActivity().applicationContext)
        newTrainingViewModel.setAvailableExercises(database.fitappkaDatabaseDao.getAllExercises())
        database.close()
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

        // Add break dialog box
        binding.addBreakButton.setOnClickListener {
            breakAlertDialogFragment.show(activity!!.supportFragmentManager, "BreakAlertDialog")
        }

        // Navigation
        binding.addExerciseButton.setOnClickListener {view: View ->
            view.findNavController().navigate(NewTrainingFragmentDirections.actionNewTrainingFragmentToExerciseSpecificationFragment())
        }

        //TODO: fix rotating issue ... (adding unnecessary item)

        // Adding Exercise to ViewModel
        var args = NewTrainingFragmentArgs.fromBundle(arguments!!)
        // NPE handler
        Log.i("NewTrainingFrag", args?.exerciseName ?: "No arguments")
        if (args?.exerciseName != "" && args?.exerciseName != null){
            newTrainingViewModel.addExercise(args.exerciseName.toString())
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // BreakDialog Observer
        breakDialogViewModel.settingDoneFlag.observe(this, Observer {
            if(breakDialogViewModel.settingDoneFlag.value ?: false){
                newTrainingViewModel.addExercise("Przerwa ".plus(breakDialogViewModel.counter.value.toString()).plus(" min"))
                breakDialogViewModel.unSetFlag()
            }
        })

        // RecycleView Observer
        newTrainingViewModel.exercisesList.observe(this, Observer {
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

    override fun onResume() {
        super.onResume()
        newTrainingViewModel.onResume()
    }

}
