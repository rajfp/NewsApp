package com.example.myapplication.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.CallResponse
import com.example.myapplication.model.NewsResponseModel
import com.example.myapplication.networkinterface.NetworkInterface
import com.example.myapplication.networkmanager.NetworkManager
import retrofit2.Call
import retrofit2.Response

class NewsRepository(context: Context) {
    var news: MutableLiveData<CallResponse<NewsResponseModel>>? = MutableLiveData()
    var retroInstance: NetworkInterface? = null

    init {
        retroInstance = NetworkManager.getRetrofitInstance(context).create(NetworkInterface::class.java)
    }

    fun getLatestNews(country: String, apiKey: String): MutableLiveData<CallResponse<NewsResponseModel>>? {
        var response = retroInstance?.getLatestNews(country, apiKey)
        response?.enqueue(object : retrofit2.Callback<NewsResponseModel> {
            override fun onFailure(call: Call<NewsResponseModel>, t: Throwable) {
                news?.value=CallResponse.error(t)
            }

            override fun onResponse(call: Call<NewsResponseModel>, response: Response<NewsResponseModel>
            ) {
                news?.value =CallResponse.success(response.body())
            }

        })
        return news
    }
}