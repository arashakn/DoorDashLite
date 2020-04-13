package com.doordash.repository

import com.doordash.api.RetrofitBuilder
import com.doordash.models.Restaurant

object MainRepository {
    suspend fun  getRestaurants (lng : Float , lat : Float, offset : Int , limit : Int) : ArrayList<Restaurant>{
        return RetrofitBuilder.restaurantsAPI.getRestaurnats(lng,lat,offset,limit)
    }
}