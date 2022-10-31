package com.fitness.enterprise.management.customer.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.repository.CustomerRepository
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.CustomerServiceEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerServiceDashboardViewModel @Inject constructor(private val customerRepository: CustomerRepository) : ViewModel() {

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

    fun createCustomer(customerDetails: CustomerDetails) {
        var validationMessage: String? = null

        if (TextUtils.isEmpty(customerDetails.customerName)) {
            validationMessage = "Please enter customer name"
        } else if (TextUtils.isEmpty(customerDetails.customerPhoneNumber)) {
            validationMessage = "Please enter customer contact"
        } else if (customerDetails.customerStatus != CustomerServiceEnum.CUSTOMER_ENQUIRED.getUserRoleAsStringForServer() && customerDetails.gymSubscriptionPlanCode == Constants.DEFAULT_VALUE) {
            validationMessage = "Please select gym subscription"
        }

        if (validationMessage != null) {
            _validationMessageLiveData.postValue(validationMessage)
        } else {
            viewModelScope.launch {
                customerRepository.createCustomer(customerDetails)
            }
        }
    }

    fun updateCustomerDetails(gymCode: String, contactNumber: String, customerDetails: CustomerDetails) {
        viewModelScope.launch {
            customerRepository.updateCustomerDetails(gymCode, contactNumber, customerDetails)
        }
    }
}