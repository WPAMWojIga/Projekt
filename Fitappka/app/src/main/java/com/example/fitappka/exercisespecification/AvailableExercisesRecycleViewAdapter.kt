package com.example.fitappka.exercisespecification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappka.R
import com.example.fitappka.database.Exercise
import com.example.fitappka.newtraining.NewTrainingViewModel

class AvailableExercisesRecycleViewAdapter(private val availableExList: List<Exercise>): RecyclerView.Adapter<AvailableExercisesRecycleViewAdapter.ViewHolder>(){


    class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        val exerciseInfo: TextView = itemView.findViewById<TextView>(R.id.exercise_info)
        val exName : TextView = itemView.findViewById(R.id.ex_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val exerciseCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.full_ex_card, parent, false)
        return ViewHolder(
            exerciseCardView
        )
    }

    override fun getItemCount(): Int = availableExList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        availableExList[position].apply{
            val info : String = exerciseBPType + "\n" + exerciseTRType
            holder.exerciseInfo.text = info
            holder.exName.text = exerciseName 

        }
    }

}