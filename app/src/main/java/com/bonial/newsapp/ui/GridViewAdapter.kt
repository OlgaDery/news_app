package com.bonial.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bonial.newsapp.NewsFeedViewModel
import com.bonial.newsapp.R
import com.bonial.newsapp.model.NewsItemViewData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_item_big.view.*

class GridViewAdapter(val viewModel : NewsFeedViewModel, val onClick: () -> Unit): RecyclerView.Adapter<BindingViewHolder>() {

    var items = mutableListOf<NewsItemViewData?>()

    fun loadItems(entries: MutableList<NewsItemViewData?>) {

        //workaround to prevent the exception: java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder
        if (itemCount > 0) {
            items.clear()
        }
        items.addAll(entries)
        notifyDataSetChanged()
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
                return R.layout.grid_item_big
            }
        }
        return R.layout.grid_item_small
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(items[position]!!)
        holder.itemView.setOnClickListener {
            onClick()
            viewModel.selectedItem = items[position]!!.item
        }

        if (position == itemCount - 7 && !viewModel.allEarliestLoaded) {
            viewModel.updateNewsList(itemCount)
        }
        Picasso.get().load(items[position]!!.item.urlToImage).into(holder.itemView.item_image)
    }

    override fun onViewRecycled(holder: BindingViewHolder) {
        super.onViewRecycled(holder)
    }

}