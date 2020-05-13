package com.doordash.api

import com.doordash.models.Restaurant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Creating an interface for defining endpoints using annotation
 * Each method of interface represent one possible API call
 */

interface RestaurantsAPI {
    @GET("/v2/restaurant/")
    suspend fun getRestaurants( @Query("lng") lng : Float, @Query("lat") lat : Float, @Query("offset") offset : Int, @Query("limit") limit : Int) : ArrayList<Restaurant>

    @GET("/v2/restaurant/{id}")
    suspend fun getRestaurantInfo(@Query("id") id : Long)

    @GET("/v2/restaurant/")
    fun getRestaurantsRx( @Query("lng") lng : Float, @Query("lat") lat : Float, @Query("offset") offset : Int, @Query("limit") limit : Int) : Single<ArrayList<Restaurant>>

    @GET("/v2/restaurant/{id}") // path parameters
    fun getRestaurantInfoRx(@Path("id") id : Long) : Single<Restaurant>
}