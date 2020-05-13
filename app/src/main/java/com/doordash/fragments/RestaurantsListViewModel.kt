package com.doordash.fragments

import androidx.lifecycle.*
import com.doordash.api.RetrofitBuilder
import com.doordash.models.Restaurant
import com.doordash.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException

class RestaurantsListViewModel : ViewModel() {
    val selectedRestaurant = MutableLiveData<Restaurant>()
    val error = MutableLiveData(false)
    private var curLng = -122.139956F
    private var curLat = 37.422740F
    private val offSet = 0
    private val limit = 50
    private val compositeDisposable  = CompositeDisposable ()

    /**
     * Utilizing new API of coroutines for live data using livedata builder function
     * getAllImages() is a suspend function declared
     * Use the liveData builder function to call getAllImages asynchronously
     * and then use emit() to emit the result for allImages liveData
     * and the result will be observed by fragment.
     *
     * The code block starts executing when LiveData becomes active and is canceled if the LiveData becomes inactive.
     * If it is canceled before completion, it is restarted if the LiveData becomes active again.
     * If it completed successfully in a previous run, it doesn't restart. Note that it is restarted only if canceled automatically.
     */

    val restaurants = liveData {
        error.value = false
        try {
            val allRestaurants = MainRepository.getRestaurants(curLng, curLat, offSet, limit)
            emit(allRestaurants)
        } catch (ex: Exception) {
            error.value = true
        }
    }

    fun fetchRestaurant(id : Long){
        error.value = false
        try {
            val disposable = RetrofitBuilder.restaurantsAPI.getRestaurantInfoRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<Restaurant>(){
                    override fun onSuccess(value: Restaurant?) {
                        value?.let {
                            selectedRestaurant.value = it
                        }
                    }
                    override fun onError(e: Throwable?) {
                        error.value = true
                    }
                })
            compositeDisposable.add(disposable)
        }catch (e : RuntimeException){
            error.value = true
        }
    }
    fun fetchAllRestaurant(){
        error.value = false
        try {
            val disposable = RetrofitBuilder.restaurantsAPI.getRestaurantsRx(curLng, curLat, offSet, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<ArrayList<Restaurant>>(){
                    override fun onSuccess(value: ArrayList<Restaurant>?) {
                        value?.let {
                            println(it.size)
                        }
                    }
                    override fun onError(e: Throwable?) {
                        error.value = true
                    }
                })
            compositeDisposable.add(disposable)
        }catch (e : RuntimeException){
            error.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
