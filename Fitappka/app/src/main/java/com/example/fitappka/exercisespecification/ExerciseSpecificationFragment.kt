package com.example.fitappka.exercisespecification

// Close keyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitappka.R
import com.example.fitappka.databinding.FragmentSpecificationExerciseBinding
import com.example.fitappka.newtraining.NewTrainingViewModel
import kotlinx.android.synthetic.main.fragment_specification_exercise.*

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
        availableExAdapter = AvailableExercisesRecycleViewAdapter(viewModel.availableExercisesList)
        layoutmanager = LinearLayoutManager(this.requireContext())

        binding.availableExRecycler.apply {
            adapter = availableExAdapter
            layoutManager = layoutmanager
        }

        /*binding.exConfirmButton.setOnClickListener {view: View ->
            // TextEdit never returns null, at least it returns empty String - ""
            *//*var text: String = binding.editText.text.toString()
            if (text != ""){
                Log.i("ExerciseSpecFragment", text)//Hide the keyboard.
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.findNavController().navigate(ExerciseSpecificationFragmentDirections.actionExerciseSpecificationFragmentToNewTrainingFragment(text))
            }
            }
            */





        return binding.root
    }
}