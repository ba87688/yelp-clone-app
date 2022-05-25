package com.example.yelpcloneapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object YelpApi {


    val retrofit: Retrofit = Retrofit.Builder().baseUrl(YelpService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: YelpService by lazy {
        retrofit.create(YelpService::class.java)
    }

    //If you want more service just add more val such as
//    val otherService: MyOtherService by lazy {
//        retrofit().create(MyOtherService::class.java
//    }

}