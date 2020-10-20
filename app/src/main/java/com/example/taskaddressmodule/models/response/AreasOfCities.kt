package com.example.taskaddressmodule.models.response

import com.google.gson.annotations.SerializedName

data class AreasOfCities(

	@SerializedName("name")
	var name: String,

	@SerializedName("shipping_charges")
	var shipping_charges: Int,

	@SerializedName("city_id")
	var city_id: Int,

	@SerializedName("area_id")
	var area_id: Int
)