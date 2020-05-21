package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.listener.ClickListener
import com.example.myapplication.model.Articles
import kotlinx.android.synthetic.main.item_list_layout.view.*

class NewsAdapter(
    private val context: Context,
    val list: List<Articles>,
    val clickListener: ClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = list.get(position)
        holder.bind(news, clickListener)
    }

    inner class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        fun bind(articles: Articles, clickListener: ClickListener) {
            view.tv_title.text = articles.title
            view.tv_published.text = articles.publishedAt
            Glide.with(context).load(list.get(adapterPosition).urlToImage).apply(RequestOptions().diskCacheStrategy(
                DiskCacheStrategy.RESOURCE)).into(view.img)
            itemView.setOnClickListener {
                clickListener.sendData(articles)
            }
        }

        override fun onClick(v: View?) {
            clickListener.sendData(list[adapterPosition])
        }


    }

}