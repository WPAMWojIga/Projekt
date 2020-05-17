package com.example.fitappka.exercisespecification

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappka.R
import com.example.fitappka.database.Exercise
import com.example.fitappka.newtraining.NewTrainingViewModel

class AvailableExercisesRecycleViewAdapter(private val viewModel : NewTrainingViewModel): RecyclerView.Adapter<AvailableExercisesRecycleViewAdapter.ViewHolder>(){

    private val availableExList = viewModel.availableExercisesList
   /* private var tracker: SelectionTracker<Long>? = null
    public val sTracker : SelectionTracker<Long>?
        get() = tracker
*/
    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        val exerciseInfo: TextView = itemView.findViewById(R.id.exercise_info)
        val exName : TextView = itemView.findViewById(R.id.ex_name)
        val exCardBackground: ConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.available_ex_card_background)
        var isSelected : Boolean = false
        fun getItemDetails() : ItemDetailsLookup.ItemDetails<Long> {
            return object : ItemDetailsLookup.ItemDetails<Long> () {
                override fun getSelectionKey(): Long? = itemId
                override fun getPosition(): Int = adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val exerciseCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.full_ex_card, parent, false)
        return ViewHolder(exerciseCardView)
    }

    fun getItem(position: Int) : Exercise = availableExList.value!![position]
    fun getPosition(exercise : Exercise) = availableExList.value!!.indexOf(exercise)

    override fun getItemCount(): Int = availableExList.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        availableExList.value!![position].apply {
            val info: String = exerciseBPType + "\n" + exerciseTRType
            val name: String = exerciseId.toString() + ". " + exerciseName
            holder.exerciseInfo.text = info
            holder.exName.text = name
        }
            holder.itemView.setOnClickListener {
                if (viewModel.exerciseSelectedPosition == -1) {
                    viewModel.exerciseSelectedPosition = position
                    holder.exCardBackground.background = ColorDrawable(Color.parseColor("#7f00ff"))
                } else if (viewModel.exerciseSelectedPosition == position) {
                    viewModel.exerciseSelectedPosition = -1
                    holder.exCardBackground.background = ColorDrawable(Color.parseColor("#cc99ff"))
                }


            }

    }

   /* public fun setTracker(t : SelectionTracker<Long>) {
        tracker = t
    }*/
}