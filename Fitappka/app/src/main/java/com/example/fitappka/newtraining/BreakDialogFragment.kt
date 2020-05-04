package com.example.fitappka.newtraining

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fitappka.R

class BreakDialogFragment : DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

//    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            listener = targetFragment as NoticeDialogListener
//        } catch (e: ClassCastException) {
//            // The activity doesn't implement the interface, throw exception
//            throw ClassCastException((context.toString() +
//                    " must implement NoticeDialogListener"))
//        }
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            // AlertDialog - Break Counter
            val builder = AlertDialog.Builder(it)

            //TODO: Pass the data between two fragments...
            builder.setPositiveButton("Ustaw", DialogInterface.OnClickListener{ dialog, id -> //listener.onDialogPositiveClick(this)
                })
                .setNegativeButton(
                    "Anuluj", DialogInterface.OnClickListener{dialog, id -> //listener.onDialogNegativeClick(this)
                    })

            builder.setView(R.layout.break_dialog)
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}