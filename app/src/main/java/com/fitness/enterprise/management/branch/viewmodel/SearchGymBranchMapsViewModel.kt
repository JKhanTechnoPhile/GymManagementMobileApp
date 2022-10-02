package com.fitness.enterprise.management.branch.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitness.enterprise.management.branch.model.LocationModel
import com.fitness.enterprise.management.branch.utils.GeoCoderUtil
import com.fitness.enterprise.management.branch.utils.LoadDataCallback
import com.fitness.enterprise.management.branch.utils.ResponseStatusCallbacks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchGymBranchMapsViewModel @Inject constructor() : ViewModel() {

    private val _getLocationInformation = MutableLiveData<ResponseStatusCallbacks<LocationModel>>()
    val getLocationInformation: LiveData<ResponseStatusCallbacks<LocationModel>>
        get() = _getLocationInformation

    fun getLocationInfo(context: Context, latitude: String, longitude: String) {
        _getLocationInformation.value = ResponseStatusCallbacks.loading(data = null)
        GeoCoderUtil.execute(context, latitude, longitude, object :
            LoadDataCallback<LocationModel> {
            override fun onDataLoaded(response: LocationModel) {
                _getLocationInformation.value = ResponseStatusCallbacks.success(response)
            }
            override fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {
                _getLocationInformation.value =
                    ResponseStatusCallbacks.error(data = null, message = "Something went wrong!")
            }
        })
    }
}