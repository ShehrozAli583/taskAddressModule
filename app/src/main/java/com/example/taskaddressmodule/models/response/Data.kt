package com.example.taskaddressmodule.models.response

import com.example.taskaddressmodule.models.request.UserAddressRequest
import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("user_addressRequests")
    var user_addressRequests: MutableList<UserAddressRequest>? = null,

    @SerializedName("cities")
    var cities: MutableList<Cities>? = null,

    @SerializedName("areas_of_cities")
    var areas_of_cities: MutableList<AreasOfCities>? = null,

    @SerializedName("address_id")
    var address_id: Int? = null,

    @SerializedName("device_id")
    var device_id: Int? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("building_name")
    var building_name: String? = null,

    @SerializedName("apartment")
    var apartment: String? = null,

    @SerializedName("street_address")
    var street_address: String? = null,

    @SerializedName("city")
    var city: String? = null,

    @SerializedName("area")
    var area: String? = null,

    @SerializedName("city_id")
    var city_id: Int? = null,

    @SerializedName("area_id")
    var area_id: Int? = null,

    @SerializedName("lat")
    var lat: Double? = null,

    @SerializedName("long")
    var long: Double? = null

)