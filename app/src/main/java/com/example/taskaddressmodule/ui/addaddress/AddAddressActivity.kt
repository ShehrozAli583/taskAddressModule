package com.example.taskaddressmodule.ui.addaddress

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.taskaddressmodule.R
import com.example.taskaddressmodule.models.request.UserAddressRequest
import com.example.taskaddressmodule.models.response.AreasOfCities
import com.example.taskaddressmodule.models.response.Cities
import com.example.taskaddressmodule.models.response.Data
import com.example.taskaddressmodule.ui.addaddress.adapters.AreaAdapter
import com.example.taskaddressmodule.ui.addaddress.adapters.CityAdapter
import com.example.taskaddressmodule.ui.addaddress.viewmodel.AddAddressViewModel
import com.example.taskaddressmodule.utils.Constants
import com.example.taskaddressmodule.utils.Preferences
import com.example.taskaddressmodule.utils.showProgressBar
import com.example.taskaddressmodule.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_address.*
import javax.inject.Inject


@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity() {
    private val addAddressViewModel: AddAddressViewModel by viewModels()

    @Inject
    lateinit var pref: Preferences

    var latitude: Double? = null
    var longitude: Double? = null
    var data: Data? = null
    var citiesArray: MutableList<Cities> = arrayListOf()
    var areaAdapter: AreaAdapter? = null
    var citiesAdapter: CityAdapter? = null
    var cityID: Int? = null
    var areaID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        // region Get Latitude and Longitude through Intent
        latitude = intent.extras!!.getDouble(Constants.LATITUDE)
        longitude = intent.extras!!.getDouble(Constants.LONGITUDE)
        // endregion Get Latitude and Longitude through Intent

        data = pref.getPreferenceObjectJson(Preferences.Keys.DATA_KEY) as Data

        citiesArray = data!!.cities!!

        citiesAdapter = CityAdapter(this, R.layout.dropdown_menu_popup_item, citiesArray)
        spCity.setAdapter(citiesAdapter)

        spCity.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPoi = adapterView.adapter.getItem(i) as Cities?
            spCity.setText(selectedPoi?.name)
            cityID = selectedPoi!!.city_id
            setAreaAdapter(cityID!!)
        }

        spArea.setOnItemClickListener { adapterView, view, i, l ->
            val selectedPoi = adapterView.adapter.getItem(i) as AreasOfCities?
            spArea.setText(selectedPoi?.name)
            areaID = selectedPoi!!.city_id
        }

        tvAddAddress.setOnClickListener {
            if (validation()) {
                val userAddress = UserAddressRequest(
                    edBuildingName.text.toString(),
                    edApartmentName.text.toString(),
                    edStreetAddress.text.toString(),
                    cityID!!,
                    areaID!!,
                    Constants.device_id,
                    Constants.lang,
                    latitude!!,
                    longitude!!
                )
                showProgressBar(pb, true)
                addAddressViewModel.addUserAddress(userAddress) { msgOrError, data ->
                    showProgressBar(pb, false)
                    if (data != null) {
                        tvAddAddressData.text = " Building Name : ${data.building_name} ,\n Apartment Name : ${data.apartment} ,\n Street Address : ${data.street_address} ,\n City ID : ${data.city_id} ,\n Area ID : ${data.area_id} ,\n Device ID : ${data.device_id} ,\n Latitude : ${data.lat} ,\n Longitude : ${data.long}"
                    } else {
                        showToast(msgOrError)
                    }
                }
            }
        }
    }

    private fun getAreasOfCities(cityID: Int): MutableList<AreasOfCities> {
        val areasArray: MutableList<AreasOfCities> = arrayListOf()
        data!!.areas_of_cities!!.forEachIndexed { index, element ->
            if (data!!.areas_of_cities!![index].city_id == cityID) {
                areasArray.add(element)
            }
        }
        return areasArray
    }

    private fun setAreaAdapter(cityID: Int) {
        areaAdapter = AreaAdapter(this, R.layout.dropdown_menu_popup_item, getAreasOfCities(cityID))
        spArea.setAdapter(areaAdapter)
    }

    private fun validation(): Boolean {
        var isValid = true
        if (spCity.text!!.trim().toString().isEmpty()) {
            showToast(resources.getString(R.string.city_error))
            isValid = false
            return isValid
        }
        if (spArea.text!!.trim().toString().isEmpty()) {
            showToast(resources.getString(R.string.area_error))
            isValid = false
            return isValid
        }

        if (edBuildingName.text!!.trim().toString().isEmpty()) {
            edBuildingName.error = (getString(R.string.building_error))
            isValid = false
            return isValid
        }
        if (edApartmentName.text!!.trim().toString().isEmpty()) {
            edApartmentName.error = getString(R.string.apartment_error)
            isValid = false
            return isValid
        }

        if (edStreetAddress.text!!.trim().toString().isEmpty()) {
            edStreetAddress.error = getString(R.string.street_address_error)
            isValid = false
            return isValid
        }

        return isValid
    }
}