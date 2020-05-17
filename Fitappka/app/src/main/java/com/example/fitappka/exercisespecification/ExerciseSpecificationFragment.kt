package com.example.fitappka.exercisespecification

// Close keyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentSpecificationExerciseBinding
import com.example.fitappka.newtraining.NewTrainingViewModel

class ExerciseSpecificationFragment: Fragment() {

    private lateinit var viewModel : NewTrainingViewModel
    private lateinit var availableExAdapter : AvailableExercisesRecycleViewAdapter

    private lateinit var layoutmanager : LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSpecificationExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_specification_exercise, container, false
        )

        viewModel = ViewModelProviders.of(requireActivity()).get(NewTrainingViewModel::class.java)
        viewModel.setAvailableExercises()
        availableExAdapter = AvailableExercisesRecycleViewAdapter(viewModel)
        layoutmanager = LinearLayoutManager(this.requireContext())
        binding.availableExRecycler.apply {
            adapter = availableExAdapter
            layoutManager = layoutmanager
        }

        binding.exConfirmButton.setOnClickListener {

            val measure = binding.exTrNumberInsert.text.toString().toIntOrNull()
            val position  = viewModel.exerciseSelectedPosition
            if ( measure != null &&
                measure > 0  && position != -1) {
                if (viewModel.addedExercises.find{ array -> array[0] == position } == null)
                viewModel.addToTraining(position, measure)
                viewModel.addExercise(viewModel.availableExercisesList.value!![position].exerciseName)
                view?.findNavController()?.navigate(ExerciseSpecificationFragmentDirections.actionExerciseSpecificationFragmentToNewTrainingFragment())

            }


        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}