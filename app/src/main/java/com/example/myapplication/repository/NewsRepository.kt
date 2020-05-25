package com.example.myapplication.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.CallResponse
import com.example.myapplication.model.NewsResponseModel
import com.example.myapplication.networkinterface.NetworkInterface
import com.example.myapplication.networkmanager.NetworkManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsRepository(context: Context) {
    var news: MutableLiveData<CallResponse<NewsResponseModel>>? = MutableLiveData()
    var retroInstance: NetworkInterface? = null

    init {
        retroInstance = NetworkManager.getRetrofitInstance(context).create(NetworkInterface::class.java)
    }

    fun getLatestNews(compositeDisposable: CompositeDisposable, country: String, apiKey: String): MutableLiveData<CallResponse<NewsResponseModel>>? {
        retroInstance?.getLatestNews(country,apiKey)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<NewsResponseModel>() {
                override fun onSuccess(t: NewsResponseModel) {
                    news?.value= CallResponse.success(t)
                }
                override fun onError(e: Throwable) {
                    news?.value= CallResponse.error(e)
                }

            })?.let { compositeDisposable.add(it) }
        return news
    }
}