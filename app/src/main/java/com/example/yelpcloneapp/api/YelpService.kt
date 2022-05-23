package com.example.yelpcloneapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpService {

    @GET("businesses/search")
    fun searchRestaurants(
        @Query("term") searchTerm:String,
        @Query("location") location: String
    ): Call<Any>
}