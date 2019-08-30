package com.bonial.newsapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bonial.newsapp.NewsFeedViewModel

import com.bonial.newsapp.R
import kotlinx.android.synthetic.main.fragment_news_feed.*

class NewsFeedFragment : Fragment() {

    private var viewModel: NewsFeedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(NewsFeedViewModel::class.java)

        viewModel!!.news.observe(activity!!, Observer {
            if (viewModel!!.observableID != it.first) {
                viewModel!!.observableID = viewModel!!.observableID + 1
                test_text.text = viewModel!!.news.value!!.second.size.toString()
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            viewModel!!.getDataFromWebServer(viewModel!!.news.value!!.second.last()!!.date)
        }
    }


}
