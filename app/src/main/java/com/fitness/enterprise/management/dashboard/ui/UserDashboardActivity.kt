package com.fitness.enterprise.management.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.fitness.enterprise.management.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_user_dashboard_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.activity_user_dashboard_bottom_navigation_view)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}