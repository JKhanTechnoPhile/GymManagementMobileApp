package com.fitness.enterprise.management.branch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.model.GymBranchCreateRequest
import com.fitness.enterprise.management.branch.repository.GymBranchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymBranchDashboardViewModel @Inject constructor(private val gymBranchRepository: GymBranchRepository) : ViewModel() {

    val gymBranchesData get() = gymBranchRepository.gymBranchesLiveData
    val gymBranchData get() = gymBranchRepository.gymBranchDetailsLiveData
    val statusLiveData get() = gymBranchRepository.statusLiveData

    fun getGymBranches() {
        viewModelScope.launch {
            gymBranchRepository.getAllGymBranches()
        }
    }

    fun getGymBranchDetails(gymBranch: GymBranch) {
        viewModelScope.launch {
            gymBranchRepository.getGymBrancheDetails(gymBranch.gymBranchId)
        }
    }

    fun createGymBranch(createGymBranchCreateRequest: GymBranchCreateRequest) {
        viewModelScope.launch {
            gymBranchRepository.createGymBranch(createGymBranchCreateRequest)
        }
    }

    fun updateGymBranch(updateGymBranchRequest: GymBranch) {
        viewModelScope.launch {
            gymBranchRepository.updateGymBranch(updateGymBranchRequest)
        }
    }

    fun deleteGymBranch(deleteGymBranchRequest: GymBranch) {
        viewModelScope.launch {
            gymBranchRepository.deleteGymBranch(deleteGymBranchRequest)
        }
    }
}