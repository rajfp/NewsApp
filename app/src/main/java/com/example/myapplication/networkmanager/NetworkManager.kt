package com.example.myapplication.networkmanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


object NetworkManager {
    fun getRetrofitInstance(context: Context): Retrofit {
        val cacheSize: Long = 10 * 1024 * 1024
        val myCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addNetworkInterceptor() { chain ->
                val originalResponse = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")
                if (cacheControl == null || cacheControl!!.contains("no-store") || cacheControl!!.contains(
                        "cache"
                    ) ||
                    cacheControl!!.contains("must-revalidate") || cacheControl!!.contains("max-age=0")
                ) {
                    originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
                } else {
                    originalResponse
                }
            }.addNetworkInterceptor() { chain ->
                var request = chain.request()
                if (!(this!!.hasNetwork(context)!!)) {
                    request = request.newBuilder()
                        .removeHeader("Pragma")
                        .cacheControl(CacheControl.Builder().maxStale(7, TimeUnit.DAYS).build())
                        .build()
                }
                chain.proceed(request)
            }.addInterceptor(interceptor)
            .build()

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}