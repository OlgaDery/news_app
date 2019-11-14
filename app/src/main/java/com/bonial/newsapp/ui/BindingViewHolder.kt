package com.bonial.newsapp.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bonial.newsapp.BR


class BindingViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}