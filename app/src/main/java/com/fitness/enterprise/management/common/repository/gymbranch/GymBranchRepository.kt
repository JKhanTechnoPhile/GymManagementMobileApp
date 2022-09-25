package com.fitness.enterprise.management.common.repository.gymbranch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.common.api.gym.branch.GymBranchApi
import com.fitness.enterprise.management.common.api.gym.branch.model.GymBranch
import com.fitness.enterprise.management.common.api.gym.branch.model.GymBranchesResponse
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.NetworkResult
import javax.inject.Inject

class GymBranchRepository @Inject constructor(private val gymBranchApi: GymBranchApi) {

    private val _gymBranchesLiveData = MutableLiveData<NetworkResult<GymBranchesResponse>>()
    val gymBranchesLiveData: LiveData<NetworkResult<GymBranchesResponse>>
        get() = _gymBranchesLiveData

    private val _gymBranchDetailsLiveData = MutableLiveData<NetworkResult<GymBranch>>()
    val gymBranchDetailsLiveData: LiveData<NetworkResult<GymBranch>>
        get() = _gymBranchDetailsLiveData

    suspend fun getAllGymBranches() {
        _gymBranchesLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = gymBranchApi.getAllGymBranches()
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _gymBranchesLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            _gymBranchesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _gymBranchesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getAllGymBranches(gymCode: Int) {
        _gymBranchDetailsLiveData.postValue(NetworkResult.Loading())
        val gymBranchDetails = gymBranchApi.getGymBranchDetails(gymCode)
        Log.d(Constants.TAG,  gymBranchDetails.body().toString())
        if (gymBranchDetails.isSuccessful && gymBranchDetails.body() != null) {
            _gymBranchDetailsLiveData.postValue(NetworkResult.Success(gymBranchDetails.body()!!))
        } else if (gymBranchDetails.errorBody() != null) {
            _gymBranchDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _gymBranchDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}