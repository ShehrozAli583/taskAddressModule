package com.example.taskaddressmodule.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.taskaddressmodule.R
import com.example.taskaddressmodule.ui.location.LocationActivity
import com.example.taskaddressmodule.ui.splash.viewmodel.SplashViewModel
import com.example.taskaddressmodule.utils.Constants
import com.example.taskaddressmodule.utils.Preferences
import com.example.taskaddressmodule.utils.showProgressBar
import com.example.taskaddressmodule.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var pref: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*Get getCitiesAndAreas Response In Callback*/
        splashViewModel.getCitiesAndAreas(Constants.device_id, Constants.lang) { msgOrError, data ->
            showProgressBar(pb, false)
            if (data != null) {
                // Todo set both cities and areas in SharedPreferences
                pref.setPreferenceObject(Preferences.Keys.DATA_KEY, data)
                startActivity(Intent(this@SplashActivity, LocationActivity::class.java))
            } else {
                showToast(msgOrError)
            }
        }
    }
}