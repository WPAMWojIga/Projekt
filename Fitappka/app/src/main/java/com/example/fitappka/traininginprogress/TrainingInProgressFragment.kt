package com.example.fitappka.traininginprogress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentTrainingInProgressBinding
import kotlin.concurrent.thread

class TrainingInProgressFragment : Fragment() {

    private lateinit var viewModel: TrainingProgressViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       /* thread { viewModel.refreshTrainingsWithExercises() }*/
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentTrainingInProgressBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_training_in_progress,
            container,
            false
        )
        viewModel  = ViewModelProviders.of(requireActivity()).get(TrainingProgressViewModel::class.java)

            viewModel.refreshCrossRefs()
            viewModel.currentExercise = 0
        thread {
            viewModel.refreshTrainingsWithExercises()
        }
        binding.trainingInProgressName.text =
            viewModel.trainingsWithExercises.
            value!![viewModel.selectedTrainingPosition]
                .training.trainingName
        binding.trainingInProgressExName.text =
            viewModel.trainingsWithExercises.value!![viewModel.selectedTrainingPosition].exercises[viewModel.currentExercise].exerciseName
        binding.trainingInProgressExInfo.text =
            viewModel.trainingExerciseCrossRef[viewModel.currentExercise].exerciseTRNumber.toString()


        binding.nextExButton.setOnClickListener{



            viewModel.currentExercise++
            if (viewModel.trainingFinished()) {
                view?.findNavController()?.navigate(TrainingInProgressFragmentDirections.actionTrainingInProgressFragmentToMainMenuFragment())
            } else {
                binding.trainingInProgressName.text =
                    viewModel.trainingsWithExercises.value!![viewModel.selectedTrainingPosition].training.trainingName
                binding.trainingInProgressExName.text =
                    viewModel.trainingsWithExercises.value!![viewModel.selectedTrainingPosition].exercises[viewModel.currentExercise].exerciseName
                binding.trainingInProgressExInfo.text =
                    viewModel.trainingExerciseCrossRef[viewModel.currentExercise].exerciseTRNumber.toString()

            }

        }

        binding.trainingExitButton.setOnClickListener {
            view?.findNavController()?.navigate(TrainingInProgressFragmentDirections.actionTrainingInProgressFragmentToMainMenuFragment())
        }


        return binding.root

    }

    override fun onResume() {
        super.onResume()
        thread {
            viewModel.refreshCrossRefs()

        }
        viewModel.currentExercise = 0
    }
}