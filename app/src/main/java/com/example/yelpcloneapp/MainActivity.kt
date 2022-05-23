package com.example.yelpcloneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.yelpcloneapp.api.YelpApi
import com.example.yelpcloneapp.api.YelpService
import com.example.yelpcloneapp.models.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val API_KEY2 = YelpService.API_KEY
        val yelpService = YelpApi.retrofitService

        yelpService.searchRestaurants("Bearer $API_KEY2","Avocado Toast", "New York").enqueue(object :Callback<YelpSearchResult>{
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                Log.i(TAG, "onResponse: ${response}")
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure: $t")
            }


        }
        )
    }
}