package com.fitness.enterprise.management.branch.utils

import android.content.Context
import android.location.Geocoder
import com.fitness.enterprise.management.branch.model.LocationModel
import com.fitness.enterprise.management.utils.Constants
import com.google.android.gms.common.util.CollectionUtils
import java.util.*

object GeoCoderUtil {

    fun execute(
        context: Context,
        latitude: String,
        longitude: String,
        callback: LoadDataCallback<LocationModel>
    ) {
        var address = Constants.EMPTY_STRING
        var cityName = Constants.EMPTY_STRING
        var areaName = Constants.EMPTY_STRING
        val locationModel: LocationModel
        try {
            val geocoder = Geocoder(context, Locale.ENGLISH)
            val addresses =
                geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            if (!CollectionUtils.isEmpty(addresses)) {
                val fetchedAddress = addresses!![0]
                if (fetchedAddress.maxAddressLineIndex > -1) {
                    address = fetchedAddress.getAddressLine(0)
                    fetchedAddress.locality?.let {
                        cityName = it
                    }
                    fetchedAddress.subLocality?.let {
                        areaName = it
                    }
                }
                locationModel = LocationModel().apply {
                    locationAddress = address
                    locationCityName = cityName
                    locationAreaName = areaName
                }
                callback.onDataLoaded(locationModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onDataNotAvailable(1, "Something went wrong!")
        }
    }
}

interface LoadDataCallback<T> {
    fun onDataLoaded(response: T) {
    }

    fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {

    }
}