package com.example.taskaddressmodule.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.taskaddressmodule.R
import com.example.taskaddressmodule.ui.addaddress.AddAddressActivity
import com.example.taskaddressmodule.utils.Constants
import com.example.taskaddressmodule.utils.showToast
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*


class LocationActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 2020
    var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // region UIListener
        tvGetLocation.setOnClickListener(this)
        tvAddAddress.setOnClickListener(this)
        // endregion UIListener
    }

    @SuppressLint("SetTextI18n")
    private fun getLastLocation() {
        // Check Runtime Permission
        if (checkPermission()) {
            // Check is Location is enabled or not
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    location = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        tvLatLong.text =
                            "You Current Location is : Long: " + location!!.longitude + "\n Lat: " + location!!.latitude + "\n" + getCityName(
                                location!!.latitude,
                                location!!.longitude
                            )
                    }
                }
            } else {
                showToast(resources.getString(R.string.turn_on_location))
            }
        } else {
            requestPermission()
        }
    }

    /*Check Runtime Permission*/
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    /*Request For Permission*/
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    /*Check Is Location Enabled or Not*/
    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    /*Get Permission Result*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug:", "You have the Permission")
            }
        }
    }

    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (checkPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            tvLatLong.text =
                "You Last Location is : Long: " + lastLocation.longitude + "\n Lat: " + lastLocation.latitude + "\n" + getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
        }
    }

    /*Convert Latitude and Longitude into Address using Geocoder*/
    private fun getCityName(lat: Double, long: Double): String {
        var cityName: String = ""
        var countryName = ""
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)

        cityName = address[0].locality
        countryName = address[0].countryName
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        return cityName
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvGetLocation -> {
                requestPermission()
                getLastLocation()
            }
            R.id.tvAddAddress -> {
                if (location != null) {
                    val i = Intent(this@LocationActivity, AddAddressActivity::class.java)
                    val bundle = Bundle()
                    bundle.putDouble(Constants.LATITUDE, location!!.latitude)
                    bundle.putDouble(Constants.LONGITUDE, location!!.longitude)
                    i.putExtras(bundle)
                    startActivity(i)
                }
            }
        }
    }
}