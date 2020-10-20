package com.example.taskaddressmodule.ui.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskaddressmodule.models.response.Data
import com.example.taskaddressmodule.network.WebResponse
import com.example.taskaddressmodule.ui.splash.repo.SplashRepo
import com.example.taskaddressmodule.utils.Constants
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@ActivityScoped
class SplashViewModel @ViewModelInject constructor(private val repo: SplashRepo) : ViewModel() {

    fun getCitiesAndAreas(
        deviceID: Int, language: String, callback: (msgOrError: String, data: Data?) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = repo.getCitiesAndAreas(deviceID, language)
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