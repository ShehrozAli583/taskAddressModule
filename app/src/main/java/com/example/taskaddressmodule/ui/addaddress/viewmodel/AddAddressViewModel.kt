package com.example.taskaddressmodule.ui.addaddress.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskaddressmodule.models.request.UserAddressRequest
import com.example.taskaddressmodule.models.response.Data
import com.example.taskaddressmodule.ui.addaddress.repo.AddAddressRepo
import com.example.taskaddressmodule.utils.Constants
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityScoped
class AddAddressViewModel @ViewModelInject constructor(private val repo: AddAddressRepo) :
    ViewModel() {

    fun addUserAddress(
        userAddressRequest: UserAddressRequest, callback: (msgOrError: String, data: Data?) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = repo.addUserAddress(userAddressRequest)
            val data = result.data

            val msgOrError = if (result.code == Constants.STATUS_CODE) {
                result.msg?.get(0) ?: "success"
            } else {
                result.errors?.get(0) ?: "error"
            }
            launch(Dispatchers.Main) {
                callback(msgOrError, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            launch(Dispatchers.Main) {
                callback("No Internet Connection", null)
            }
        }
    }
}