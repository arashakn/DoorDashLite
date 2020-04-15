package com.doordash.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.doordash.R
import kotlinx.android.synthetic.main.fragment_restaurant_detailed.*


class RestaurantDetailedFragment : Fragment() {
    lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name") ?: ""
        }
    }

    lateinit var viewModel :RestaurantsListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_detailed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        activity?.let {
            viewModel = ViewModelProvider(it).get(RestaurantsListViewModel::class.java)
            viewModel.selectedRestaurant.observe(viewLifecycleOwner , Observer {
                rvRestaurantTitle.text = it.name
            })
        }

    }
}
