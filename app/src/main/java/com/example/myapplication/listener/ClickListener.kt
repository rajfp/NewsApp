package com.example.myapplication.listener

import com.example.myapplication.model.Articles

interface ClickListener {
    fun sendData(articles: Articles)
}