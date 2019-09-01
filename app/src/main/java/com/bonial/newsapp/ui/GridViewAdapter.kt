package com.bonial.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bonial.newsapp.NewsFeedViewModel
import com.bonial.newsapp.R

class GridViewAdapter(val viewModel : NewsFeedViewModel): RecyclerView.Adapter<BindingViewHolder>() {

    var items = mutableListOf<Any?>()

    fun loadItems(entries: MutableList<Any?>) {
        items.clear()
        items.addAll(entries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.size > position) {
            if (position == 0 || position % 7 == 0) {
                return R.layout.grid_item_bigl
            }
        }
        return R.layout.grid_item_small
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(items[position]!!)
        holder.itemView.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.feed_fr_to_item_fr)

        }
        if (position == items.size - 7 && !viewModel.allEarliestLoaded) {
            viewModel.getDataFromWebServer((viewModel.news.value!!.second.last()!!.publishedAt), true)
        }
    }

}