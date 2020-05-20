package com.example.myapplication.networkmanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor



object NetworkManager{
    fun getRetrofitInstance(context:Context):Retrofit {
        val cacheSize: Long = 10 * 1024 * 1024
        val myCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient=OkHttpClient.Builder().addInterceptor(interceptor).build()
//        val okHttpClient = OkHttpClient.Builder()
//            .cache(myCache)
//            // Add an Interceptor to the OkHttpClient.
//            .addInterceptor { chain ->
//
//                // Get the request from the chain.
//                var request = chain.request()
//
//                /*
//            *  Leveraging the advantage of using Kotlin,
//            *  we initialize the request and change its header depending on whether
//            *  the device is connected to Internet or not.
//            */
//                request = if (hasNetwork(context)!!)
//                /*
//            *  If there is Internet, get the cache that was stored 5 seconds ago.
//            *  If the cache is older than 5 seconds, then discard it,
//            *  and indicate an error in fetching the response.
//            *  The 'max-age' attribute is responsible for this behavior.
//            */
//                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
//                else
//                /*
//            *  If there is no Internet, get the cache that was stored 7 days ago.
//            *  If the cache is older than 7 days, then discard it,
//            *  and indicate an error in fetching the response.
//            *  The 'max-stale' attribute is responsible for this behavior.
//            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
//            */
//                    request.newBuilder().header(
//                        "Cache-Control",
//                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//                    ).build()
//                // End of if-else statement
//
//                // Add the modified request to the chain.
//                chain.proceed(request)
//            }
//            .build()

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}