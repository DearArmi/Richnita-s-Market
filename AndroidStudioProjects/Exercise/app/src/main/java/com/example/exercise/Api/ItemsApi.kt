package com.example.exercise.Api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface ItemsApi {
    @GET("hiring.json")
    suspend fun getItems():String
}
private var retrofit = Retrofit.Builder().baseUrl("https://fetch-hiring.s3.amazonaws.com/")
    .addConverterFactory(ScalarsConverterFactory.create()).build()

var service = retrofit.create(ItemsApi::class.java)
