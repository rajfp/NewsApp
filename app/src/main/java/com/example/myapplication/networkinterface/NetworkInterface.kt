package com.example.myapplication.networkinterface

import com.example.myapplication.model.NewsResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {
    @GET("v2/top-headlines")
    fun getLatestNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Single<NewsResponseModel>

}