package com.fitness.enterprise.management.auth.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.auth.di.SessionManager
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserResponse
import com.fitness.enterprise.management.utils.Constants.TAG
import com.fitness.enterprise.management.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val authUserApi: AuthUserApi, private val sessionManager: SessionManager) {

    private val _registerUserLiveData = MutableLiveData<NetworkResult<RegisterUserResponse>>()
    val registerUserLiveData: LiveData<NetworkResult<RegisterUserResponse>>
        get() = _registerUserLiveData

    private val _loginUserLiveData = MutableLiveData<NetworkResult<LoginUserResponse>>()
    val loginUserLiveData: LiveData<NetworkResult<LoginUserResponse>>
        get() = _loginUserLiveData

    suspend fun registerUser(registerUserRequest: RegisterUserRequest) {
        _registerUserLiveData.postValue(NetworkResult.Loading())
        val registerUserResponse = authUserApi.registerUser(registerUserRequest)
        Log.d(TAG, registerUserResponse.body().toString())
        if (registerUserResponse.isSuccessful && registerUserResponse.body() != null) {
            _registerUserLiveData.postValue(NetworkResult.Success(registerUserResponse.body()!!))
        } else if (registerUserResponse.errorBody() != null) {
            val errorObj = JSONObject(registerUserResponse.errorBody()!!.charStream().readText())
            _registerUserLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _registerUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun loginUser(loginUserRequest: LoginUserRequest) {
        _loginUserLiveData.postValue(NetworkResult.Loading())
        val loginUserResponse = authUserApi.loginUser(loginUserRequest)
        Log.d(TAG, loginUserResponse.body().toString())
        if (loginUserResponse.isSuccessful && loginUserResponse.body() != null) {
            val loginResponseFromServer = loginUserResponse.body()!!
            loginResponseFromServer.userName = loginUserRequest.username
            loginResponseFromServer.roleType = loginUserRequest.roleType
            _loginUserLiveData.postValue(NetworkResult.Success(loginResponseFromServer))
            sessionManager.loginUserLiveData.postValue(loginResponseFromServer)
        } else if (loginUserResponse.errorBody() != null) {
            val errorObj = JSONObject(loginUserResponse.errorBody()!!.charStream().readText())
            _loginUserLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _loginUserLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}