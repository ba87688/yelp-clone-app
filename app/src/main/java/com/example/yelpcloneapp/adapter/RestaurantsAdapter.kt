package com.example.yelpcloneapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.yelpcloneapp.R
import com.example.yelpcloneapp.databinding.ItemRestaurantBinding
import com.example.yelpcloneapp.models.YelpRestaurant

class RestaurantsAdapter(val context:Context) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>(){
    private var restaurantList: List<YelpRestaurant> = emptyList()

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemRestaurantBinding.bind(itemView)

        fun bind(restaurant: YelpRestaurant) {

            binding.tvName.text = restaurant.name
            binding.ratingBar.rating = restaurant.rating.toFloat()
            binding.tvReviews.text = "${restaurant.numReviews} Reviews"
            binding.tvAddress.text = restaurant.location.address
            binding.tvDistance.text = restaurant.displayDistance()
            binding.tvDollar.text = restaurant.price


//            itemView.findViewById<TextView>(R.id.tvName).text = restaurant.name
//            itemView.findViewById<RatingBar>(R.id.ratingBar).rating = restaurant.rating.toFloat()
//            itemView.findViewById<TextView>(R.id.tvReviews).text = "${restaurant.numReviews} Reviews"
//            itemView.findViewById<TextView>(R.id.tvAddress).text = restaurant.location.address
//            itemView.findViewById<TextView>(R.id.tvDistance).text = restaurant.displayDistance()
//            itemView.findViewById<TextView>(R.id.tvDollar).text = restaurant.price
            var imageUrl = binding.imageView
//                itemView.findViewById<ImageView>(R.id.imageView)

            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions().transform(
                CenterCrop(),RoundedCorners(20)
            )).into(imageUrl)
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

    fun setData(newPersonList:List<YelpRestaurant>){
        val diffUtil = MyDiffUtil(restaurantList , newPersonList)

        val diffResults = DiffUtil.calculateDiff(diffUtil)
        restaurantList = newPersonList
        diffResults.dispatchUpdatesTo(this)
    }

}