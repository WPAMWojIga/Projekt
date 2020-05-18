package com.example.fitappka.traininginprogress

import android.content.Context.AUDIO_SERVICE
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentTrainingInProgressBinding
import kotlin.concurrent.thread

class TrainingInProgressFragment : Fragment() {

    private lateinit var viewModel: TrainingProgressViewModel
    private lateinit var audioManager: AudioManager
    private lateinit var soundPool : SoundPool
    private val streamType  = AudioManager.STREAM_MUSIC
    private val MAX_STREAMS = 5
    private var loaded : Boolean = false
    private var soundIdExDone : Int = 0
    private var volume : Float = 0f
    private lateinit var binding : FragmentTrainingInProgressBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       /* thread { viewModel.refreshTrainingsWithExercises() }*/

        audioManager = requireActivity().getSystemService(AUDIO_SERVICE) as AudioManager
        val currentVolumeIndex : Int = audioManager.getStreamVolume(streamType)
        val maxVolumeIndex : Int = audioManager.getStreamMaxVolume(streamType)
        volume = maxVolumeIndex.toFloat()
        requireActivity().volumeControlStream = streamType

        val audioAttributes : AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_GAME)
            .build()

        val builder : SoundPool.Builder = SoundPool.Builder()
        builder.setAudioAttributes(audioAttributes)
            .setMaxStreams(MAX_STREAMS)
        soundPool = builder.build()

        soundPool.setOnLoadCompleteListener { soundPool: SoundPool, sampleId: Int, status: Int ->
            loaded = true
        }

        soundIdExDone = soundPool.load( requireContext(),R.raw.point_blank, 1)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
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

        if (viewModel.isCurrentTimeMeasured()) {
            viewModel.setTimerForExercise()
            viewModel.countdownTimer.secondsLeft.observe(viewLifecycleOwner,
                Observer {
                    binding.trainingInProgressExInfo.text = it.toString()
                    if (it == 0) {
                        playExDoneSound()
                    }
                })
        }

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
                if (viewModel.isCurrentTimeMeasured()) {
                    viewModel.setTimerForExercise()
                    viewModel.countdownTimer.secondsLeft.observe(viewLifecycleOwner,
                        Observer {
                            binding.trainingInProgressExInfo.text = it.toString()
                            if (it == 0) {
                                playExDoneSound()
                            }
                        })
                }
                }
                }

        binding.trainingExitButton.setOnClickListener {
            view?.findNavController()?.navigate(TrainingInProgressFragmentDirections.actionTrainingInProgressFragmentToMainMenuFragment())
        }

        binding.trainingInProgressBackground.setOnClickListener {
            if(viewModel.isCurrentTimeMeasured() && !viewModel.countdownTimer.finished) viewModel.startExTimer()
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


    fun playExDoneSound() {
        if (loaded) {
            val leftVolume : Float = volume
            val rightVolume : Float = volume

            val streamId : Int = soundPool
                .play(soundIdExDone, leftVolume, rightVolume, 1, 0, 1f)
        }
    }
}