package com.bonial.newsapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bonial.newsapp.NewsFeedViewModel

import com.bonial.newsapp.R
import com.bonial.newsapp.databinding.FragmentSingleItemBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_single_item.*

class SingleItemFragment : Fragment() {

    private lateinit var binding: FragmentSingleItemBinding
    lateinit var viewModel: NewsFeedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(NewsFeedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_item, container, false)
        binding.lifecycleOwner = activity
        binding.item = viewModel.selectedItem
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(viewModel.selectedItem!!.urlToImage).into(img_picture)
    }

}
