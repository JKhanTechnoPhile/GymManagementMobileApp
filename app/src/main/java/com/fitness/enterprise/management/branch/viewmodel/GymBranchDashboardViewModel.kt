package com.fitness.enterprise.management.branch.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.model.GymBranchCreateRequest
import com.fitness.enterprise.management.branch.repository.GymBranchRepository
import com.fitness.enterprise.management.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymBranchDashboardViewModel @Inject constructor(private val gymBranchRepository: GymBranchRepository) : ViewModel() {

    val gymBranchesData get() = gymBranchRepository.gymBranchesLiveData
    val gymBranchData get() = gymBranchRepository.gymBranchDetailsLiveData
    val statusLiveData get() = gymBranchRepository.statusLiveData

    private val _validationMessageLiveData = MutableLiveData<String>()
    val validationMessageLiveData: LiveData<String>
        get() = _validationMessageLiveData

    fun getGymBranches() {
        viewModelScope.launch {
            gymBranchRepository.getAllGymBranches()
        }
    }

    fun getGymBranchDetails(gymBranch: GymBranch) {
        viewModelScope.launch {
            gymBranchRepository.getGymBranchDetails(gymBranch.gymBranchId)
        }
    }

    fun createGymBranch(createGymBranchCreateRequest: GymBranchCreateRequest) {

        var validationMessage: String? = null

        if (TextUtils.isEmpty(createGymBranchCreateRequest.gymName)) {
            validationMessage = "Please enter gym name"
        } else if (TextUtils.isEmpty(createGymBranchCreateRequest.gymContact)) {
            validationMessage = "Please enter gym contact number"
        }  else if (TextUtils.isEmpty(createGymBranchCreateRequest.gymFullAddress) || TextUtils.isEmpty(createGymBranchCreateRequest.gymLocationLat) || TextUtils.isEmpty(createGymBranchCreateRequest.gymLocationLong)) {
            validationMessage = "Please provide correct branch location"
        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            viewModelScope.launch {
                gymBranchRepository.createGymBranch(createGymBranchCreateRequest)
            }
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