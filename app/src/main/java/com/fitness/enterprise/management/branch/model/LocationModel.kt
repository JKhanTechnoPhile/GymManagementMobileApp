package com.fitness.enterprise.management.branch.model

import com.fitness.enterprise.management.utils.Constants
import java.io.Serializable

class LocationModel : Serializable {
    var locationAddress: String = Constants.EMPTY_STRING
    var locationCityName: String = Constants.EMPTY_STRING
    var locationAreaName: String = Constants.EMPTY_STRING
    var locationLatitude: String = Constants.EMPTY_STRING
    var locationLongitude: String = Constants.EMPTY_STRING
}
