package com.example.yelpcloneapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpcloneapp.R
import com.example.yelpcloneapp.models.YelpRestaurant

class RestaurantsAdapter(val context:Context,val restaurantList: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurant) {

            itemView.findViewById<TextView>(R.id.tvName).text = restaurant.name
        }
    }


    override fun getItemCount(): Int {
        return restaurantList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val restaurant = restaurantList[position]
        holder.bind(restaurant)

    }

}