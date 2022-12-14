package com.fitness.enterprise.management.auth.viewmodel

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserResponse
import com.fitness.enterprise.management.auth.repository.UserAuthRepository
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.model.GymBranchesResponse
import com.fitness.enterprise.management.branch.repository.GymBranchRepository
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.Constants.TAG
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository, private val gymBranchRepository: GymBranchRepository) : ViewModel() {

    val registerUserResponseLiveData : LiveData<NetworkResult<RegisterUserResponse>>
    get() = userAuthRepository.registerUserLiveData

    val loginUserResponseLiveData : LiveData<NetworkResult<LoginUserResponse>>
        get() = userAuthRepository.loginUserLiveData

    val gymBranchesResponseLiveData : LiveData<NetworkResult<GymBranchesResponse>>
        get() = gymBranchRepository.gymBranchesLiveData

    private val _selectedGymBranchLiveData = MutableLiveData<GymBranch>()
    val selectedGymBranchLiveData: LiveData<GymBranch>
        get() = _selectedGymBranchLiveData

    private val _validationMessageLiveData = MutableLiveData<String>()
    val validationMessageLiveData: LiveData<String>
        get() = _validationMessageLiveData

    private var selectedBranchIndex: Int? = null

    fun registerUser(emailId: String,
                     password: String,
                     phoneNumber: String,
                     roleType: Int,
                     userIdNumber: String,
                     userIdType: String,
                     userName: String,
                     userType: String) {
        val selectedUserBranch = selectedGymBranchLiveData.value
        val selectedGymBranchCode = selectedUserBranch?.gymCode ?: Constants.EMPTY_STRING

        var validationMessage: String? = null

        if (roleType == Constants.DEFAULT_VALUE || TextUtils.isEmpty(userType)) {
            validationMessage = "Please choose user type"
        } else if (TextUtils.isEmpty(userName)) {
            validationMessage = "Please enter user name"
        } else if (TextUtils.isEmpty(phoneNumber)) {
            validationMessage = "Please enter user phone number"
        } else if (!TextUtils.isEmpty(emailId) && !Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            validationMessage = "Please enter valid email id"
        } else if (!TextUtils.isEmpty(userIdType) && TextUtils.isEmpty(userIdNumber)) {
            validationMessage = "Please enter valid user id number"
        } else if (TextUtils.isEmpty(selectedGymBranchCode)) {
            validationMessage = "Please choose gym branch"
        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            val registerUserRequest = RegisterUserRequest(emailId, selectedGymBranchCode, password, phoneNumber, roleType, userIdNumber, userIdType, userName, userType)
            viewModelScope.launch {
                userAuthRepository.registerUser(registerUserRequest)
            }
        }
    }

    fun loginUser(userName: String, password: String, roleType: Int, userType: String) {

        var validationMessage: String? = null

        if (roleType == Constants.DEFAULT_VALUE || TextUtils.isEmpty(userType)) {
            validationMessage = "Please choose user type"
        } else if (TextUtils.isEmpty(userName)) {
            validationMessage = "Please enter user"
        } else if (TextUtils.isEmpty(password)) {
            validationMessage = "Please enter user password"
        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            val loginUserRequest = LoginUserRequest(password, userName, roleType)
            viewModelScope.launch {
                userAuthRepository.loginUser(loginUserRequest)
            }
        }
    }

    fun getAllGymBranches() {
        viewModelScope.launch {
            gymBranchRepository.getAllGymBranches()
        }
    }

    fun showGymBranchSelection(activity: Activity, gymBranchesResponse: GymBranchesResponse) {

//        val singleItems = gymBranchesResponse.filter { gymBranch -> gymBranch.gymBranchId != 1 }.map { gymBranch -> gymBranch.gymName}.toTypedArray()
        val singleItems = gymBranchesResponse.map { gymBranch -> gymBranch.gymName}.toTypedArray()
//        val singleItems = gymBranchesResponse.map { gymBranch ->
//            val stringBuilder = StringBuilder()
//            stringBuilder.append(gymBranch.gymName)
//            stringBuilder.append(", ")
//            stringBuilder.append(gymBranch.gymFullAddress)
//            stringBuilder.toString()
//        }.toTypedArray()
        val checkedItem = 0
//      https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter
        MaterialAlertDialogBuilder(activity)
            .setTitle("Select Branch")
            .setNeutralButton("Cancel") { dialog, which ->
            }
            .setPositiveButton("OK") { dialog, which ->
                this.selectedBranchIndex?.let {
                    _selectedGymBranchLiveData.postValue(gymBranchesResponse.get(it))
                }
            }
            .setSingleChoiceItems(singleItems, checkedItem){ dialog, which ->
                Log.d(TAG, "Selected Branch: ${singleItems.get(which)}")
                this.selectedBranchIndex = which
            }
            .show()
    }
}