package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.Constants
import com.example.myapplication.model.Articles
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        displayNews()

    }

    private fun displayNews() {
        var article: Articles? = intent?.getParcelableExtra(Constants.OBJECT)
        Glide.with(this).load(article?.urlToImage).into(news_img)
        tv_title_text?.text = article?.title
        tv_date.text = article?.publishedAt
        tv_desc?.text = article?.description
        tv_desc.text = article?.content
        tv_url.text = R.string.for_more.toString() + article?.url
        tv_author.text = article?.author
    }
}
