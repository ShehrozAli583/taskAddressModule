package com.example.taskaddressmodule.models.response

import com.google.gson.annotations.SerializedName

data class Cities(

    @SerializedName("city_id")
    var city_id: Int,

    @SerializedName("name")
    var name: String
)