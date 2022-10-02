package com.fitness.enterprise.management.branch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.branch.api.GymBranchApi
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.model.GymBranchCreateRequest
import com.fitness.enterprise.management.branch.model.GymBranchesResponse
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class GymBranchRepository @Inject constructor(private val gymBranchApi: GymBranchApi) {

    private val _gymBranchesLiveData = MutableLiveData<NetworkResult<GymBranchesResponse>>()
    val gymBranchesLiveData: LiveData<NetworkResult<GymBranchesResponse>>
        get() = _gymBranchesLiveData

    private val _gymBranchDetailsLiveData = MutableLiveData<NetworkResult<GymBranch>>()
    val gymBranchDetailsLiveData: LiveData<NetworkResult<GymBranch>>
        get() = _gymBranchDetailsLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getAllGymBranches() {
        _gymBranchesLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = gymBranchApi.getAllGymBranches()
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _gymBranchesLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _gymBranchesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _gymBranchesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getGymBranchDetails(gymCode: Int) {
        _gymBranchDetailsLiveData.postValue(NetworkResult.Loading())
        val gymBranchDetails = gymBranchApi.getGymBranchDetails(gymCode)
        Log.d(Constants.TAG,  gymBranchDetails.body().toString())
        if (gymBranchDetails.isSuccessful && gymBranchDetails.body() != null) {
            _gymBranchDetailsLiveData.postValue(NetworkResult.Success(gymBranchDetails.body()!!))
        } else if (gymBranchDetails.errorBody() != null) {
            val errorObj = JSONObject(gymBranchDetails.errorBody()!!.charStream().readText())
            _gymBranchDetailsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _gymBranchDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createGymBranch(gymBranchCreateRequest: GymBranchCreateRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymBranchApi.createGymBranch(gymBranchCreateRequest)
        handleResponse(response, "Branch created successfully")
    }

    suspend fun updateGymBranch(gymBranchUpdateRequest: GymBranch) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymBranchApi.updateGymBranch(gymBranchUpdateRequest.gymBranchId, gymBranchUpdateRequest)
        handleResponse(response, "Branch updated successfully")
    }

    private fun handleResponse(response: Response<GymBranch>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun deleteGymBranch(gymBranchDeleteRequest: GymBranch) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymBranchApi.deleteGymBranch(gymBranchDeleteRequest.gymBranchId)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success("Branch deleted successfully"))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}