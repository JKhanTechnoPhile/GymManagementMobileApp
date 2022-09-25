package com.fitness.enterprise.management.auth.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserResponse
import com.fitness.enterprise.management.utils.Constants.TAG
import com.fitness.enterprise.management.utils.NetworkResult
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val authUserApi: AuthUserApi) {

    private val _registerUserLiveData = MutableLiveData<NetworkResult<RegisterUserResponse>>()
    val registerUserLiveData: LiveData<NetworkResult<RegisterUserResponse>>
    get() = _registerUserLiveData

    private val _loginUserLiveData = MutableLiveData<NetworkResult<LoginUserResponse>>()
    val loginUserLiveData: LiveData<NetworkResult<LoginUserResponse>>
        get() = _loginUserLiveData

    suspend fun registerUser(registerUserRequest: RegisterUserRequest) {
        _registerUserLiveData.postValue(NetworkResult.Loading())
        val registerUserResponse = authUserApi.registerUser(registerUserRequest)
        Log.d(TAG,  registerUserResponse.body().toString())
        if (registerUserResponse.isSuccessful && registerUserResponse.body() != null) {
            _registerUserLiveData.postValue(NetworkResult.Success(registerUserResponse.body()!!))
        } else if (registerUserResponse.errorBody() != null) {
            _registerUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _registerUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun loginUser(loginUserRequest: LoginUserRequest) {
        _loginUserLiveData.postValue(NetworkResult.Loading())
        val loginUserResponse = authUserApi.loginUser(loginUserRequest)
        Log.d(TAG,  loginUserResponse.body().toString())
        if (loginUserResponse.isSuccessful && loginUserResponse.body() != null) {
            _loginUserLiveData.postValue(NetworkResult.Success(loginUserResponse.body()!!))
        } else if (loginUserResponse.errorBody() != null) {
            _loginUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _loginUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}