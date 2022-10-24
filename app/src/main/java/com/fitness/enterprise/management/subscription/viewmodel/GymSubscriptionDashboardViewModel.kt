package com.fitness.enterprise.management.subscription.viewmodel

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
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.subscription.model.GymSubscriptionCreateRequest
import com.fitness.enterprise.management.subscription.repository.GymSubscriptionRepository
import com.fitness.enterprise.management.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymSubscriptionDashboardViewModel @Inject constructor(private val gymSubscriptionRepository: GymSubscriptionRepository) : ViewModel() {

    val gymSubscriptionsData get() = gymSubscriptionRepository.gymSubscriptionsLiveData
    val gymSubscriptionData get() = gymSubscriptionRepository.gymSubscriptionDetailsLiveData
    val statusLiveData get() = gymSubscriptionRepository.statusLiveData

    private val _validationMessageLiveData = MutableLiveData<String>()
    val validationMessageLiveData: LiveData<String>
        get() = _validationMessageLiveData

    fun getGymSubscriptions() {
        viewModelScope.launch {
            gymSubscriptionRepository.getAllGymSubscriptions()
        }
    }

    fun getGymSubscriptionsByCode(gymCode: Int) {
        viewModelScope.launch {
            gymSubscriptionRepository.getAllGymSubscriptionsByGymCode(gymCode)
        }
    }

    fun getGymSubscriptionDetails(gymBranch: GymBranch, gymSubscription: GymSubscription) {
        viewModelScope.launch {
            gymSubscriptionRepository.getGymSubscriptionDetails(gymBranch.gymBranchId, gymSubscription.gymSubscriptionId)
        }
    }

    fun createGymSubscription(createGymSubscriptionRequest: GymSubscriptionCreateRequest) {

        var validationMessage: String? = null

        if (TextUtils.isEmpty(createGymSubscriptionRequest.gymSubscriptionName)) {
            validationMessage = "Please enter gym subscription plan name"
        } else if (TextUtils.isEmpty(createGymSubscriptionRequest.gymSubscriptionFrequency)) {
            validationMessage = "Please select gym subscription plan"
        } else if (TextUtils.isEmpty(createGymSubscriptionRequest.gymSubscriptionBaseFare)) {
            validationMessage = "Please select gym subscription plan fare"
        } else if (TextUtils.isEmpty(createGymSubscriptionRequest.gymSubscriptionStartDate)) {
            validationMessage = "Please enter gym subscription start date"
        } else if (TextUtils.isEmpty(createGymSubscriptionRequest.gymSubscriptionEndDate)) {
            validationMessage = "Please enter gym subscription end date"
        } else if (TextUtils.isEmpty(createGymSubscriptionRequest.gymBranchCode)) {
            validationMessage = "Please select gym, under which subsciprion plan is required"
        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            viewModelScope.launch {
                gymSubscriptionRepository.createGymSubscription(createGymSubscriptionRequest)
            }
        }
    }

    fun updateGymSubscription(gymCode: Int, gymSubscriptionCode: Int, gymBranchUpdateRequest: GymSubscription) {
        viewModelScope.launch {
            gymSubscriptionRepository.updateGymSubscription(gymCode, gymSubscriptionCode, gymBranchUpdateRequest)
        }
    }

    fun deleteGymBranch(gymCode: Int, gymSubscriptionCode: Int) {
        viewModelScope.launch {
            gymSubscriptionRepository.deleteGymSubscription(gymCode, gymSubscriptionCode)
        }
    }
}