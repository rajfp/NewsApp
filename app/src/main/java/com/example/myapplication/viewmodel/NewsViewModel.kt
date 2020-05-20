package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.NewsResponseModel
import com.example.myapplication.repository.NewsRepository

class NewsViewModel(context: Context):ViewModel() {

  var newsData:MutableLiveData<NewsResponseModel>
  var newsRepo:NewsRepository

    init {
        newsData= MutableLiveData()
        newsRepo= NewsRepository(context)
    }

    fun fetchNews(country:String, apiKey:String)=newsRepo.getLatestNews(country, apiKey)
}