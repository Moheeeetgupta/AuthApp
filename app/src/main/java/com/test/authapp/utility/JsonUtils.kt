package com.test.authapp.utility

import android.content.Context
import org.json.JSONObject

object JsonUtils {

    fun getCountryList(context: Context, jsonFileName: String) =
        parseJson(context.loadJSONFromAssets(jsonFileName))

    private fun Context.loadJSONFromAssets(fileName: String) =
        applicationContext.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }


    private fun parseJson(jsonString: String): MutableList<String> {
        val result = mutableListOf<String>()
        try {
            // Parse the JSON string
            val jsonObject = JSONObject(jsonString)

            // Access the "data" object
            val dataObject = jsonObject.getJSONObject("data")

            // Loop through all the keys in the "data" object
            val keysIterator = dataObject.keys()
            while (keysIterator.hasNext()) {
                val countryCode = keysIterator.next() // e.g., "DZ", "AO", "BJ", ...
                val countryData = dataObject.getJSONObject(countryCode)

                val countryName = countryData.getString("country")
                val region = countryData.getString("region")
                result.add("$countryName, $region [$countryCode]")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

}