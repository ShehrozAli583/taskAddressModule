package com.example.taskaddressmodule.ui.splash.repo

import com.example.taskaddressmodule.network.RetrofitClient
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SplashRepo @Inject constructor() {

    suspend fun getCitiesAndAreas(deviceID: Int, language: String) =
        RetrofitClient.retrofitClientInstance.getCitiesAndAreas(deviceID, language)

}