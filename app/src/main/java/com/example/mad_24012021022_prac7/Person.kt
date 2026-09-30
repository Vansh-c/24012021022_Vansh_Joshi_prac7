package com.example.mad_24012021022_prac7

import java.io.Serializable
import org.json.JSONObject

class Person(
    var id: String,
    var name: String,
    var emailId: String,
    var phoneNo: String,
    var address: String,
    var latitude: Double,
    var longitude: Double
) : Serializable {

    constructor(jsonObject: JSONObject) : this(
        id = "",
        name = "",
        emailId = "",
        phoneNo = "",
        address = "",
        latitude = 0.0,
        longitude = 0.0
    ) {
        id = jsonObject.optString("id", "")
        emailId = jsonObject.optString("email", "")
        phoneNo = jsonObject.optString("phone", "")

        val profileJson = jsonObject.optJSONObject("profile")
        if (profileJson != null) {
            name = profileJson.optString("name", "")
            address = profileJson.optString("address", "")

            val locationJson = profileJson.optJSONObject("location")
            if (locationJson != null) {
                // FIXED: API uses "lat" and "long"
                latitude = locationJson.optDouble("lat", locationJson.optDouble("latitude", 0.0))
                longitude = locationJson.optDouble("long", locationJson.optDouble("longitude", 0.0))
            }
        } else {
            name = jsonObject.optString("name", "")
            address = jsonObject.optString("address", "")
        }
    }
}