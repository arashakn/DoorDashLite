package com.doordash.fragments

import androidx.lifecycle.*
import com.doordash.models.Restaurant
import com.doordash.repository.MainRepository
import kotlinx.coroutines.launch

class RestaurantsListViewModel : ViewModel() {
    lateinit var restaurants : LiveData<ArrayList<Restaurant>>
    val selectedRestaurant   =  MutableLiveData<Restaurant>()

    init {
        viewModelScope.launch {
            getRestaurants()
        }
    }

    fun getRestaurants(lng : Float =37.422740F, lat : Float = -122.139956F , offset : Int = 0 , limit : Int = 50 ){

        restaurants= liveData {
            val allRestaurants = MainRepository.getRestaurants(lng,lat,offset,limit)
            emit(allRestaurants)
        }
    }
}
