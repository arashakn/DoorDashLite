package com.doordash.repository

import android.content.SharedPreferences
import com.doordash.AppApplication
import com.doordash.api.RetrofitBuilder
import com.doordash.models.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MainRepository {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "fav_list"
    val sharedPref: SharedPreferences?
    val editor : SharedPreferences.Editor ?
    val listOfFav : ArrayList<String>
    val SP_KEY = "FAV_LIST"

    init {
        sharedPref= AppApplication.applicationContext()?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPref?.edit()
        listOfFav = getArrayListOfFav(SP_KEY)
    }
    suspend fun  getRestaurants (lng : Float , lat : Float, offset : Int , limit : Int) : ArrayList<Restaurant>{
        return RetrofitBuilder.restaurantsAPI.getRestaurants(lng,lat,offset,limit)
    }
    suspend fun  getRestaurant(id : Long) {
        return RetrofitBuilder.restaurantsAPI.getRestaurantInfo(id)
    }


    fun getArrayListOfFav(key: String? = SP_KEY): ArrayList<String> {
        val gson = Gson()
        val json = sharedPref?.getString(key, null)
        if(json == null){
            return ArrayList<String>()
        }
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveArrayList(list: ArrayList<String>, key: String? = SP_KEY) {
        val editor = sharedPref?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString(key, json)
        editor?.apply() // This line is IMPORTANT !!!
    }
}