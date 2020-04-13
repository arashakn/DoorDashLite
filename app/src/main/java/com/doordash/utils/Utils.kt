package com.doordash.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.doordash.AppApplication

fun hasNetwork(): Boolean {
    var isConnected = false // Initial Value
    val connectivityManager = AppApplication.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}