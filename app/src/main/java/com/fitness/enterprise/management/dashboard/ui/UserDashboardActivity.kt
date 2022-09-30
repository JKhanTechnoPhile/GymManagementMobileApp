package com.fitness.enterprise.management.dashboard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.fitness.enterprise.management.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        val navController = Navigation.findNavController(this, R.id.activity_user_dashboard_nav_host_fragment)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.activity_user_dashboard_bottom_navigation_view)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}