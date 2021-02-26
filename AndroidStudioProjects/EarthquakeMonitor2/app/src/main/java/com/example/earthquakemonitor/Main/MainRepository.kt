package com.example.earthquakemonitor.Main

import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.service
import com.example.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val database: EqDatabase) {

    suspend fun fetchEarthQuakes(sortByMagnitude: Boolean):MutableList<Earthquake> {

        return withContext(Dispatchers.IO) {

            val eqString = service.getLastHourEq()
            val eqList = parseEqResult(eqString)

            database.eqDao.insertAll(eqList)

            fetchEarthQuakesFromDb(sortByMagnitude)

        }
    }

    suspend fun fetchEarthQuakesFromDb(sortByMagnitude: Boolean):MutableList<Earthquake> {

        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                database.eqDao.getEarqueakesMAg()
            } else {
                database.eqDao.getEarqueakes()
            }
        }
    }

    private fun parseEqResult(eqString: String):MutableList<Earthquake> {

        val eqJasonObject = JSONObject(eqString)
        val featureJsonArray = eqJasonObject.getJSONArray("features")

        val eqList = mutableListOf<Earthquake>()

        for (i in 0 until featureJsonArray.length()) {
            val featuresJsonObject = featureJsonArray[i] as JSONObject
            val id = featuresJsonObject.getString("id")

            val propertiesJsonObject = featuresJsonObject.getJSONObject("properties")
            val magnitude = propertiesJsonObject.getDouble("mag")
            val place = propertiesJsonObject.getString("place")
            val time = propertiesJsonObject.getLong("time")

            val geometryJsonObject = featuresJsonObject.getJSONObject("geometry")
            val coordinatesJsonArray = geometryJsonObject.getJSONArray("coordinates")
            val longitude = coordinatesJsonArray.getDouble(0)
            val latitude = coordinatesJsonArray.getDouble(1)

            val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}
