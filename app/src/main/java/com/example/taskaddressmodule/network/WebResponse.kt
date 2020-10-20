package com.example.taskaddressmodule.network

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.taskaddressmodule.models.response.Data
import com.google.gson.annotations.SerializedName


data class WebResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("msg")
    var msg: MutableList<String>? = null,

    @SerializedName("errors")
    var errors: MutableList<String>? = null,

    @SerializedName("data")
    var data: Data? = null
)