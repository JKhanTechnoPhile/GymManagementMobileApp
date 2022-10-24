package com.fitness.enterprise.management.subscription.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.fitness.enterprise.management.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GymSubscriptionDashboardActivity : AppCompatActivity() {

//    https://medium.com/androiddevelopers/navigationui-d21fd4f5c318

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gym_subscription_dashboard)
        val mainToolBar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(mainToolBar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_user_gym_subscription) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        mainToolBar.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
    }
}