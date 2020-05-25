package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.NewsRepository
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel(context: Context) : ViewModel() {

    var newsRepo: NewsRepository = NewsRepository(context)
    var compositeDisposable= CompositeDisposable()

    fun fetchNews(country: String, apiKey: String) = newsRepo.getLatestNews(compositeDisposable,country, apiKey)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}