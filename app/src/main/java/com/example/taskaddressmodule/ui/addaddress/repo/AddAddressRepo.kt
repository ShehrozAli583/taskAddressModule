package com.example.taskaddressmodule.ui.addaddress.repo

import com.example.taskaddressmodule.models.request.UserAddressRequest
import com.example.taskaddressmodule.network.RetrofitClient
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AddAddressRepo @Inject constructor() {

    suspend fun addUserAddress(userAddressRequest: UserAddressRequest) =
        RetrofitClient.retrofitClientInstance.addUserAddress(userAddressRequest)

}