package com.doordash.api

import com.doordash.models.Restaurant
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsAPI {
    @GET("/v2/restaurant/")
    suspend fun getRestaurnats(@Query("lat") lat : Float, @Query("lng") lng : Float, @Query("offset") offset : Int , @Query("limit") limit : Int) : ArrayList<Restaurant>

}