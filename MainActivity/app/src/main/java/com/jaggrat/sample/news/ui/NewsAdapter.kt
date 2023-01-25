package com.jaggrat.sample.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaggrat.sample.R
import com.jaggrat.sample.news.data.News
import kotlinx.android.synthetic.main.newslist_item.view.*

// Taking advantage of DiffUtil with ListAdapter
class NewsAdapter : ListAdapter<News, NewsAdapter.CustomViewHolder>(SampleItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.newslist_item, parent, false)
        return CustomViewHolder(view)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindTo(news:News){
            itemView.title.text= news.title
            Glide.with(itemView.context)
                .load(news.image)
                .centerCrop()
                .error(R.color.teal_700)
                .into(itemView.thumbnail);
        }
    }

    class SampleItemDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem

    }

}