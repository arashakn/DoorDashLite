package com.doordash.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doordash.R
import com.doordash.models.Restaurant
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import java.lang.Exception

class RestaurantsAdapter(
    val restaurants: ArrayList<Restaurant> = ArrayList(),
    val onRestaurantClickListener: OnRestaurantClickListener
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>() {

    private val picasso = Picasso.get()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_list_item, parent, false)
        return RestaurantsViewHolder(view, onRestaurantClickListener)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class RestaurantsViewHolder(
        val view: View,
        onRestaurantClickListener: OnRestaurantClickListener
    ) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            val restaurant = restaurants[position]

            val ivRestaurant = view.ivRestaurant
            restaurant.coverImageUrl?.let {
                picasso.load(it)
                    .error(R.drawable.ic_image_gray)
                    .placeholder(R.drawable.ic_image_gray)
                    .resize(100, 100)
                    .into(ivRestaurant, object : Callback {
                        override fun onSuccess() {
                        }

                        override fun onError(e: Exception?) {
                        }
                    })
            }
            view.tvTitle.text = restaurant.name
            view.tvType.text = restaurant.description
            view.setOnClickListener {
                onRestaurantClickListener.onRestaurantClick(adapterPosition)
            }
            view.tvStatus.text = restaurant.status
        }
    }

    fun updateRestaurants(list: ArrayList<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(list)
        notifyDataSetChanged()
    }
}

interface OnRestaurantClickListener {
    public fun onRestaurantClick(position: Int)
}