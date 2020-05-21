package com.example.myapplication.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.NewsResponseModel
import com.example.myapplication.networkinterface.NetworkInterface
import com.example.myapplication.networkmanager.NetworkManager
import com.example.myapplication.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Response

class NewsRepository(context: Context) {
    var news: MutableLiveData<NewsResponseModel>?
    var retroInstance: NetworkInterface? = null

    init {
        news = MutableLiveData()
        retroInstance =
            NetworkManager.getRetrofitInstance(context).create(NetworkInterface::class.java)
    }

    fun getLatestNews(country: String, apiKey: String): MutableLiveData<NewsResponseModel>? {
        var response = retroInstance?.getLatestNews(country, apiKey)
        response?.enqueue(object : retrofit2.Callback<NewsResponseModel> {
            override fun onFailure(call: Call<NewsResponseModel>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<NewsResponseModel>,
                response: Response<NewsResponseModel>
            ) {
                news?.value = response.body()
            }

        })
        return news
    }
}