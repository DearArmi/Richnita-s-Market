package com.example.earthquakemonitor.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface EqApiService {
    @GET("all_hour.geojson")
    suspend fun getLastHourEq(): String
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

var service = retrofit.create(EqApiService::class.java)