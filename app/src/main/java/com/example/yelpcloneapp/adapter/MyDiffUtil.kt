package com.example.yelpcloneapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.yelpcloneapp.models.YelpRestaurant

class MyDiffUtil(
    private val oldList: List<YelpRestaurant>,
    private val newList: List<YelpRestaurant>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {

        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            return oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            return oldList[oldItemPosition].location != newList[newItemPosition].location -> {
                false
            }
            return oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            else -> true
        }

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}