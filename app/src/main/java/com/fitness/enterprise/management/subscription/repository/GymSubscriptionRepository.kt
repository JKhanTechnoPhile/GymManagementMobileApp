package com.fitness.enterprise.management.subscription.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.subscription.api.GymSubscriptionApi
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.subscription.model.GymSubscriptionCreateRequest
import com.fitness.enterprise.management.subscription.model.GymSubscriptionResponse
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class GymSubscriptionRepository @Inject constructor(private val gymSubscriptionApi: GymSubscriptionApi) {

    private val _gymSubscriptionsLiveData = MutableLiveData<NetworkResult<GymSubscriptionResponse>>()
    val gymSubscriptionsLiveData: LiveData<NetworkResult<GymSubscriptionResponse>>
        get() = _gymSubscriptionsLiveData

    private val _gymSubscriptionDetailsLiveData = MutableLiveData<NetworkResult<GymSubscription>>()
    val gymSubscriptionDetailsLiveData: LiveData<NetworkResult<GymSubscription>>
        get() = _gymSubscriptionDetailsLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getAllGymSubscriptions() {
        _gymSubscriptionsLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = gymSubscriptionApi.getAllGymSubscriptions()
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _gymSubscriptionsLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _gymSubscriptionsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _gymSubscriptionsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getAllGymSubscriptionsByGymCode(gymCode: Int) {
        _gymSubscriptionsLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = gymSubscriptionApi.getAllGymSubscriptionsByGymCode(gymCode)
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _gymSubscriptionsLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _gymSubscriptionsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _gymSubscriptionsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getGymSubscriptionDetails(gymCode: Int, gymSubscriptionCode: Int) {
        _gymSubscriptionDetailsLiveData.postValue(NetworkResult.Loading())
        val gymBranchDetails = gymSubscriptionApi.getGymSubscriptionDetails(gymCode, gymSubscriptionCode)
        Log.d(Constants.TAG,  gymBranchDetails.body().toString())
        if (gymBranchDetails.isSuccessful && gymBranchDetails.body() != null) {
            _gymSubscriptionDetailsLiveData.postValue(NetworkResult.Success(gymBranchDetails.body()!!))
        } else if (gymBranchDetails.errorBody() != null) {
            val errorObj = JSONObject(gymBranchDetails.errorBody()!!.charStream().readText())
            _gymSubscriptionDetailsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _gymSubscriptionDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createGymSubscription(createGymSubscriptionRequest: GymSubscriptionCreateRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymSubscriptionApi.createGymSubscription(createGymSubscriptionRequest)
        handleResponse(response, "Gym Subscription created successfully")
    }

    suspend fun updateGymSubscription(gymCode: Int, gymSubscriptionCode: Int, gymBranchUpdateRequest: GymSubscription) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymSubscriptionApi.updateGymSubscription(gymCode, gymSubscriptionCode, gymBranchUpdateRequest)
        handleResponse(response, "Gym Subscription updated successfully")
    }

    private fun handleResponse(response: Response<GymSubscription>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun deleteGymSubscription(gymCode: Int, gymSubscriptionCode: Int) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = gymSubscriptionApi.deleteGymSubscription(gymCode, gymSubscriptionCode)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success("Gym Subscription  deleted successfully"))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}