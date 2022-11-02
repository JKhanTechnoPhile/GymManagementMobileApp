package com.fitness.enterprise.management.auth.di

import androidx.lifecycle.MutableLiveData
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var loginUserLiveData = MutableLiveData<LoginUserResponse>()
}