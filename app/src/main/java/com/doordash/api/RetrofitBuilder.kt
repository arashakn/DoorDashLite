package com.doordash.api

import com.doordash.AppApplication
import com.doordash.utils.hasNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    const val BASE_URL = "https://api.doordash.com"
    const val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(
        AppApplication.applicationContext().cacheDir,
        cacheSize
    ) //Caches HTTP and HTTPS responses to the filesystem so they may be reused, saving time and bandwidth.
    private val httpClient: OkHttpClient // http client

    init {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor() //OkHttp interceptor which logs HTTP request and response data.
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()
    }


    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val restaurantsAPI: RestaurantsAPI by lazy {
        retrofitBuilder.build().create(RestaurantsAPI::class.java)
    }
}