package com.example.yelpcloneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpcloneapp.adapter.RestaurantsAdapter
import com.example.yelpcloneapp.api.YelpApi
import com.example.yelpcloneapp.api.YelpService
import com.example.yelpcloneapp.databinding.ActivityMainBinding
import com.example.yelpcloneapp.models.YelpRestaurant
import com.example.yelpcloneapp.models.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"

class MainActivity : AppCompatActivity() {


    private var avocado = "Avocado Toast"
    private val restaurants = mutableListOf<YelpRestaurant>()
    private val adapter = RestaurantsAdapter(this)

//    private val adapter = RestaurantsAdapter()
    private val API_KEY2 = YelpService.API_KEY
    private val yelpService = YelpApi.retrofitService


    private lateinit var binding: ActivityMainBinding
    var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.setData(restaurants)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recycler = binding.recyclerView
        recycler.adapter = adapter
        val linearLayout =  LinearLayoutManager(this)
        recycler.layoutManager = linearLayout

        getRequest()
        loading = true

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

//                visible items
                val visibleItemCount: Int = linearLayout.childCount
                val pastVisibleItem: Int = linearLayout.findFirstCompletelyVisibleItemPosition()
                Log.i(TAG, "onScrolled visible count: $visibleItemCount")
                Log.i(TAG, "onScrolled pastvisible item: $pastVisibleItem")
                val totalNumOfItems = adapter.itemCount
                Log.i(TAG, "onScrolled: item count $totalNumOfItems")
                Log.i(TAG, "onScrolled: $loading")
//                if(!loading){
//                    Log.i(TAG, "onScrolled: nesteddd     $loading")

                    if(visibleItemCount+pastVisibleItem >=totalNumOfItems ){
                        getRequest()
                    }
//                }

                super.onScrolled(recyclerView, dx, dy)
            }
        }

        )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as (SearchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(TAG, "onQueryTextSubmit: Hello")
                if (query != null) {
                    if (query.trim() != "") {
                        avocado = query.trim().toString()
                        getRequest()

                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                Log.i(TAG, "onQueryTextChange: there are you")

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    fun getRequest() {
        yelpService.searchRestaurants("Bearer $API_KEY2", avocado, "New York")
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {

                    Log.i(TAG, "onResponse: ${response}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "onResponse: did not get proper response")
                        return
                    }
                    restaurants.removeAll(restaurants)
                    restaurants.addAll(body.restaurants)

                    Log.i(TAG, "onResponse: request made")
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure: $t")
                }


            }
            )
    }
}