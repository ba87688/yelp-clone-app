package com.example.yelpcloneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View.inflate
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpcloneapp.adapter.RestaurantsAdapter
import com.example.yelpcloneapp.api.YelpApi
import com.example.yelpcloneapp.api.YelpService
import com.example.yelpcloneapp.models.YelpRestaurant
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

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        val recycler = findViewById<RecyclerView>(R.id.recycler_view)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        val API_KEY2 = YelpService.API_KEY
        val yelpService = YelpApi.retrofitService

        yelpService.searchRestaurants("Bearer $API_KEY2","Avocado Toast", "New York").enqueue(object :Callback<YelpSearchResult>{
            override fun onResponse(
                call: Call<YelpSearchResult>,
                response: Response<YelpSearchResult>
            ) {
                Log.i(TAG, "onResponse: ${response}")
                val body = response.body()
                if(body==null){
                    Log.w(TAG, "onResponse: did not get proper response", )
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure: $t")
            }


        }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem  = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as(SearchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(TAG, "onQueryTextSubmit: Hello")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                Log.i(TAG, "onQueryTextChange: there are you")
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}