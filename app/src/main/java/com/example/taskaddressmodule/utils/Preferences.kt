package com.example.taskaddressmodule.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.taskaddressmodule.models.response.Data
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class required Hilt and KTX (extension)
 */

@Singleton
class Preferences @Inject constructor(@ApplicationContext appContext: Context) {

    private val pref: SharedPreferences =
        appContext.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)

    fun setPreferenceObject(key: String, modal: Any?) = pref.edit(true) {
        val gson = Gson()
        val jsonObject = gson.toJson(modal)
        putString(key, jsonObject)
    }

    fun getPreferenceObjectJson(key: String): Any? {
        val json = pref.getString(key, "")
        val gson = Gson()
        return if (!json.isNullOrEmpty()) {
            gson.fromJson<Data>(json, Data::class.java)
        } else null
    }

    companion object {
        private const val PREF_FILE_NAME = "TASK_PREF"
    }

    object Keys {
        const val DATA_KEY = "data"
    }
}
