package com.doordash.adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.doordash.R
import com.doordash.models.Restaurant
import com.doordash.repository.MainRepository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import java.lang.Exception

class RestaurantsAdapter(
    val restaurants: ArrayList<Restaurant> = ArrayList(),
    val onRestaurantClickListener: OnRestaurantClickListener,
    context: Activity?
) : RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>(), Filterable {

    private val picasso = Picasso.get()
    val width: Int
    val height: Int
    val favList: ArrayList<String>
    val restaurantsAll: ArrayList<Restaurant>


    init {
        val displayMetrics = DisplayMetrics()
        context?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        /**
         * get the width of screen in order to resize the images accordingly
         */
        height = (displayMetrics.heightPixels / 5)
        width = height
        favList = MainRepository.getArrayListOfFav()
        restaurantsAll = ArrayList(restaurants)
    }

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
                    .resize(width, height)
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
            val id = restaurant.id.toString()
            val fav = favList
            var favCopy = ""
            if (fav.contains(id)) {
                favCopy = "remove from Fav"
            } else {
                favCopy = "add to Fav"
            }

//            val favCopy = if (favList.contains(restaurant.id)) "remove from Fav" else "add to Fav"
            view.btnFav.text = favCopy
            view.btnFav.setOnClickListener {
                if (favList.contains(restaurant.id.toString())) { // removing from Fav.
                    favList.remove(restaurant.id.toString())
                    view.btnFav.text = "add to Fav"
                } else { //adding to Fav.
                    favList.add(restaurant.id.toString())
                    view.btnFav.text = "remove from Fav"
                }
                MainRepository.saveArrayList(favList)
            }
        }
    }

    fun updateRestaurants(list: ArrayList<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(list)
        restaurantsAll.clear()
        restaurantsAll.addAll(list)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return filt
    }

    val filt = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {// run background thread
            val filterList = ArrayList<Restaurant>()
            if (constraint.isNullOrEmpty()) {
                filterList.addAll(restaurantsAll)
            } else{
                for(res in restaurantsAll){
                    if(res.description.toLowerCase().contains(constraint)){
                        filterList.add(res)
                    }
                }
            }
            val filterResult = FilterResults()
            filterResult.values = filterList
            return filterResult
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults?
        ) {//runs on Ui thread
          restaurants.clear()
            restaurants.addAll(results?.values as Collection<Restaurant>)
            notifyDataSetChanged()
        }
    }
}

interface OnRestaurantClickListener {
    fun onRestaurantClick(position: Int)
}
