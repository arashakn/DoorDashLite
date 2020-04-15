package com.doordash.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.doordash.R
import com.doordash.adapters.OnRestaurantClickListener
import com.doordash.adapters.RestaurantsAdapter
import com.doordash.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.images_list_fragment.*

class RestaurantsListFragment : Fragment(), OnRestaurantClickListener {

    private lateinit var viewModel: RestaurantsListViewModel
    private lateinit var restaurantsAdapter :RestaurantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.images_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restaurantsAdapter = RestaurantsAdapter(onRestaurantClickListener =  this)

        activity?.let {
            rvRestaurants.apply {
                adapter = restaurantsAdapter
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    ))
            }
            viewModel = ViewModelProvider(it).get(RestaurantsListViewModel::class.java)
            observeViewModels()
        }
    }

    private fun observeViewModels(){
        EspressoIdlingResource.increment()
        viewModel.restaurants.observe(viewLifecycleOwner,  Observer{
            if(it.isNullOrEmpty()) {
                restaurantsAdapter.updateRestaurants(it)
                EspressoIdlingResource.increment()
            }
        }
        )
    }

    override fun onRestaurantClick(position: Int) {
        val restaurant = restaurantsAdapter.restaurants[position]
        activity?.let {
            viewModel.selectedRestaurant.value = restaurant
            val bundle = Bundle()
            bundle.apply {
                putString("name", restaurant.name)
            }
            val fragment = RestaurantDetailedFragment().apply {
                arguments = bundle
            }
            it.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView ,fragment).addToBackStack(null).commitAllowingStateLoss()
        }
    }
}
