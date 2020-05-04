package com.example.fitappka.newtraining

import android.app.AlertDialog
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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.fitappka.databinding.FragmentTrainingNewBinding

// Navigation
import androidx.navigation.findNavController

// ViewModel Architecture
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_training_new.*

class NewTrainingFragment: BreakDialogFragment.NoticeDialogListener, Fragment() {

    private lateinit var viewModel: NewTrainingViewModel
    private val exerciseListRecycleViewAdapter: ExerciseListRecycleViewAdapter = ExerciseListRecycleViewAdapter(listOf(), { viewModel.removeExercise(it) })

    private val breakAlertDialogFragment: BreakDialogFragment = BreakDialogFragment()

    // The dialog fragment receives a reference to this Fragment through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        // User touched the dialog's positive button
        viewModel.addExercise("Przerwa")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User touched the dialog's negative button
    }

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

        // Add break dialog box
        binding.addBreakButton.setOnClickListener {
            //breakAlertDialogFragment.setTargetFragment(this, 0)
            breakAlertDialogFragment.show(activity!!.supportFragmentManager, "BreakAlertDialog")
        }

        // Navigation
        binding.addExerciseButton.setOnClickListener {view: View ->
            view.findNavController().navigate(NewTrainingFragmentDirections.actionNewTrainingFragmentToExerciseSpecificationFragment())
        }

        // ViewModel
        viewModel = ViewModelProviders.of(this).get(NewTrainingViewModel::class.java)

        //TODO: fix rotating issue ... (adding unnecessary item)

        // Adding Exercise to ViewModel
        var args = NewTrainingFragmentArgs.fromBundle(arguments!!)
        // NPE handler
        Log.i("NewTrainingFrag", args?.exerciseName ?: "No arguments")
        if (args?.exerciseName != "" && args?.exerciseName != null){
            viewModel.addExercise(args.exerciseName.toString())
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

}
