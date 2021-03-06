package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.constants.Constants
import com.example.myapplication.listener.ClickListener
import com.example.myapplication.model.Articles
import com.example.myapplication.model.CallResponse
import com.example.myapplication.model.NewsResponseModel
import com.example.myapplication.networkinterface.NetworkInterface
import com.example.myapplication.networkmanager.NetworkManager
import com.example.myapplication.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(), ClickListener {


    lateinit var newViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newViewModel = NewsViewModel(this)
        newViewModel.fetchNews(Constants.COUNTRY, Constants.API_KEY)
            ?.observe(this, Observer { t ->
               when(t.status){
                   CallResponse.Status.SUCCESS->{
                       t?.data?.articles?.let { setRecyclerView(it) }
                   }
                   CallResponse.Status.ERROR->{
                       Toast.makeText(this,t.message,Toast.LENGTH_LONG).show()
                   }
               }
            })
    }

    private fun setRecyclerView(data: List<Articles>) {
        var layoutManager = LinearLayoutManager(this)
        var adapter = NewsAdapter(this, data, this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
    }

    override fun sendData(articles: Articles) {
        var intent = Intent(this, DisplayActivity::class.java)
        intent.putExtra(Constants.OBJECT, articles)
        startActivity(intent)
    }

}
