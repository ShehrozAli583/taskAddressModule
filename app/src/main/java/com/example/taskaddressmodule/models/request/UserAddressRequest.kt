package com.example.taskaddressmodule.models.request

import com.google.gson.annotations.SerializedName

data class UserAddressRequest(

    @SerializedName("building_name")
    var building_name: String,

    @SerializedName("apartment")
    var apartment: String,

    @SerializedName("street_address")
    var street_address: String,

    @SerializedName("city_id")
    var city_id: Int,

    @SerializedName("area_id")
    var area_id: Int,

    @SerializedName("device_id")
    var device_id: Int,

    @SerializedName("lang")
    var lang: String,

    @SerializedName("lat")
    var lat: Double,

    @SerializedName("long")
    var long: Double
)