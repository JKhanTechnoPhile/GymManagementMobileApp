package com.fitness.enterprise.management.branch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitness.enterprise.management.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GymBranchDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gym_branch_dashboard)
    }
}