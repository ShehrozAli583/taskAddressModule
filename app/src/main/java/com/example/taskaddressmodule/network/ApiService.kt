package com.example.taskaddressmodule.network

import com.example.taskaddressmodule.models.request.UserAddressRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("v2/add-user-address")
    suspend fun addUserAddress(@Body userAddressRequest: UserAddressRequest): WebResponse

    @GET("v2/get-user-addresses")
    suspend fun getCitiesAndAreas(
        @Query("device_id") deviceId: Int,
        @Query("lang") lang: String
    ): WebResponse
}