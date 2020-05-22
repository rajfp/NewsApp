package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.NewsRepository

class NewsViewModel(context: Context) : ViewModel() {

    var newsRepo: NewsRepository = NewsRepository(context)

    fun fetchNews(country: String, apiKey: String) = newsRepo.getLatestNews(country, apiKey)
}