package com.fitness.enterprise.management.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.repository.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository) : ViewModel() {

    fun registerUser(registerUserRequest: RegisterUserRequest) {
        viewModelScope.launch {
            userAuthRepository.registerUser(registerUserRequest)
        }
    }

    fun loginUser(loginUserRequest: LoginUserRequest) {
        viewModelScope.launch {
            userAuthRepository.loginUser(loginUserRequest)
        }
    }
}