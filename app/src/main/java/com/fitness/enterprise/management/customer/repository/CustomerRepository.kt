package com.fitness.enterprise.management.customer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.customer.api.CustomerApi
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.model.CustomersResponse
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val customerApi: CustomerApi) {

    private val _customersLiveData = MutableLiveData<NetworkResult<CustomersResponse>>()
    val customersLiveData: LiveData<NetworkResult<CustomersResponse>>
        get() = _customersLiveData

    private val _customerDetailsLiveData = MutableLiveData<NetworkResult<CustomerDetails>>()
    val customerDetailsLiveData: LiveData<NetworkResult<CustomerDetails>>
        get() = _customerDetailsLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getAllCustomers() {
        _customersLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = customerApi.getAllCustomers()
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _customersLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _customersLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _customersLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getAllCustomersByGymCode(gymCode: String) {
        _customersLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = customerApi.getAllCustomersByGymCode(gymCode)
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _customersLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _customersLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _customersLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getCustomerById(customerId: Int) {
        _customerDetailsLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = customerApi.getCustomerById(customerId)
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _customerDetailsLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _customerDetailsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _customerDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getCustomerByGymCodeAndId(gymCode: String, customerId: Int) {
        _customerDetailsLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = customerApi.getCustomerByGymCodeAndId(gymCode, customerId)
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _customerDetailsLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _customerDetailsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _customerDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getCustomerByGymCodeAndContact(gymCode: String, contactNumber: String) {
        _customerDetailsLiveData.postValue(NetworkResult.Loading())
        val allGymBranches = customerApi.getCustomerByGymCodeAndContact(gymCode, contactNumber)
        Log.d(Constants.TAG,  allGymBranches.body().toString())
        if (allGymBranches.isSuccessful && allGymBranches.body() != null) {
            _customerDetailsLiveData.postValue(NetworkResult.Success(allGymBranches.body()!!))
        } else if (allGymBranches.errorBody() != null) {
            val errorObj = JSONObject(allGymBranches.errorBody()!!.charStream().readText())
            _customerDetailsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _customerDetailsLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun createCustomer(customerDetails: CustomerDetails) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = customerApi.createCustomer(customerDetails)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success("Gym Subscription created successfully"))
            _customerDetailsLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun updateCustomerToRegister(customerId: Int, customerDetails: CustomerDetails) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = customerApi.updateCustomerToRegister(customerId, customerDetails)
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success("Customer added successfully"))
            _customerDetailsLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun updateCustomerDetails(gymCode: String, contactNumber: String, customerDetails: CustomerDetails) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = customerApi.updateCustomer(gymCode, contactNumber, customerDetails)
        handleResponse(response, "Customer added successfully")
    }

    private fun handleResponse(response: Response<CustomerDetails>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _statusLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}