package com.bonial.newsapp.ui


import android.content.Intent
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_single_item.*

class SingleItemFragment : Fragment() {

    private lateinit var binding: FragmentSingleItemBinding
    private lateinit var viewModel: NewsFeedViewModel

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
        Glide.with(this).load(viewModel.selectedItem!!.urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(img_picture)

        link_text_view.setOnClickListener {
            val myWebLink = Intent(Intent.ACTION_VIEW)
            myWebLink.data = Uri.parse(viewModel.selectedItem!!.url)
            context!!.startActivity(myWebLink)
        }
    }

}
