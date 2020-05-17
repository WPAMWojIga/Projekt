package com.example.fitappka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.fitappka.databinding.ActivityMainBinding

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.fitappka.database.FitappkaDatabase
import com.example.fitappka.database.FitappkaRepository
import com.example.fitappka.newtraining.BreakDialogFragment

class MainActivity : AppCompatActivity() {
    companion object {
        var database: FitappkaDatabase? = null
    }

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Getting binding class from layout
        @Suppress("UNUSED_VARIABLE")
        database = FitappkaDatabase.getInstance(this)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
