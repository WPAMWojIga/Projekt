package com.example.fitappka.traininginprogress

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappka.R
import com.example.fitappka.database.FitappkaDatabaseDao
import com.example.fitappka.database.Training
import kotlinx.android.synthetic.main.training_select_card.view.*

class AvailableTrainingsRecyclerAdapter(allTrainings : List<FitappkaDatabaseDao.TrainingWithExercises>) : RecyclerView.Adapter<AvailableTrainingsRecyclerAdapter.ViewHolder>() {

    var selectedTrainingPosition = -1
    private var _availableTrainingsList  = allTrainings
    val availableTrainingsList : List<FitappkaDatabaseDao.TrainingWithExercises>
        get() = _availableTrainingsList

   class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {

       val trainingCardBackground: ConstraintLayout =
           itemView.findViewById(R.id.available_trainings_background)
       val trainingName: TextView = itemView.findViewById(R.id.available_training_name)
       val trainingInfo: TextView = itemView.findViewById(R.id.available_training_info)

       }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableTrainingsRecyclerAdapter.ViewHolder {
        val trainingCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.training_select_card, parent, false)
        return ViewHolder(trainingCardView)
    }

    override fun getItemCount(): Int  = availableTrainingsList.size

    override fun onBindViewHolder(
        holder: AvailableTrainingsRecyclerAdapter.ViewHolder,
        position: Int
    ) {
        _availableTrainingsList[position].apply {
            /*holder.trainingName.text = trainingName
            holder.trainingInfo.text = trainingBPType*/
            var exs  : String = ""
            holder.trainingName.text = training.trainingName
            for (i in 0..exercises.lastIndex) {
                exs += exercises[i].exerciseName + "\n"
            }
            holder.trainingInfo.text = exs
        }

        holder.itemView.setOnClickListener{
            if (selectedTrainingPosition == -1 ) {
                selectedTrainingPosition = position
                holder.trainingCardBackground.background  = ColorDrawable(Color.parseColor("#7f00ff"))
            } else if ( selectedTrainingPosition == position ){
                selectedTrainingPosition = -1
                holder.trainingCardBackground.background  = ColorDrawable(Color.parseColor("#cc99ff"))
            }
        }
    }

    fun updateAvailableTrainings(trainings : List<FitappkaDatabaseDao.TrainingWithExercises>) {
        _availableTrainingsList = trainings
        notifyDataSetChanged()
    }

}