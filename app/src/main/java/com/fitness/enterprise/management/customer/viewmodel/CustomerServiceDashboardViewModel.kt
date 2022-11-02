package com.fitness.enterprise.management.customer.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.auth.di.SessionManager
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerServiceDashboardViewModel @Inject constructor(private val customerRepository: CustomerRepository, private val sessionManager: SessionManager) : ViewModel() {

    val customersData get() = customerRepository.customersLiveData
    val customerDetailsData get() = customerRepository.customerDetailsLiveData
    val statusLiveData get() = customerRepository.statusLiveData

    private val _validationMessageLiveData = MutableLiveData<String>()
    val validationMessageLiveData: LiveData<String>
        get() = _validationMessageLiveData

    fun getAllCustomers() {
        viewModelScope.launch {
            customerRepository.getAllCustomers()
        }
    }

    fun getAllCustomersByGymCode(gymCode: String) {
        viewModelScope.launch {
            customerRepository.getAllCustomersByGymCode(gymCode)
        }
    }

    fun getCustomerById(customerId: Int) {
        viewModelScope.launch {
            customerRepository.getCustomerById(customerId)
        }
    }

    fun getCustomerByGymCodeAndId(gymCode: String, customerId: Int) {
        viewModelScope.launch {
            customerRepository.getCustomerByGymCodeAndId(gymCode, customerId)
        }
    }

    fun getCustomerByGymCodeAndContact(gymCode: String, contactNumber: String) {
        viewModelScope.launch {
            customerRepository.getCustomerByGymCodeAndContact(gymCode, contactNumber)
        }
    }

    fun getBranchCode() : String? {
        return sessionManager.loginUserLiveData.value?.loggedInUser?.gymBranchCode
    }

    fun enquireCustomer(customerDetails: CustomerDetails) {
        viewModelScope.launch {
            customerRepository.createCustomer(customerDetails)
        }
    }

    fun registerCustomer(customerDetails: CustomerDetails) {
        var validationMessage: String? = null

        if (customerDetails.id == null) {
            validationMessage = "Customer doesn't have system generated Id"
        } else if (TextUtils.isEmpty(customerDetails.customerName)) {
            validationMessage = "Please enter customer name"
        } else if (TextUtils.isEmpty(customerDetails.customerPhoneNumber)) {
            validationMessage = "Please enter customer contact"
        } else if (!TextUtils.isEmpty(customerDetails.customerEmailId) && !Patterns.EMAIL_ADDRESS.matcher(customerDetails.customerEmailId).matches()) {
            validationMessage = "Please enter valid email id"
        } else if (!TextUtils.isEmpty(customerDetails.customerIdType) && TextUtils.isEmpty(customerDetails.customerIdProof)) {
            validationMessage = "Please enter valid user id number"
        }
//        else if (customerDetails.customerStatus != CustomerServiceEnum.CUSTOMER_ENQUIRED.getUserRoleAsStringForServer() && customerDetails.gymSubscriptionPlanCode == Constants.DEFAULT_VALUE) {
//            validationMessage = "Please select gym subscription"
//        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            viewModelScope.launch {
                customerDetails.id?.let {
                    customerRepository.updateCustomerToRegister(it, customerDetails)
                }
            }
        }
    }

    fun updateCustomerDetails(gymCode: String, contactNumber: String, customerDetails: CustomerDetails) {
        viewModelScope.launch {
            customerRepository.updateCustomerDetails(gymCode, contactNumber, customerDetails)
        }
    }
}