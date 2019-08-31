package com.bonial.newsapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bonial.newsapp.NewsFeedViewModel

import com.bonial.newsapp.R
import com.bonial.newsapp.getOrientation
import kotlinx.android.synthetic.main.fragment_news_feed.*

class NewsFeedFragment : Fragment() {

    private var viewModel: NewsFeedViewModel? = null
    var adapter: GridViewAdapter? = null
    var recentRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(NewsFeedViewModel::class.java)
        adapter = GridViewAdapter(viewModel!!)

        viewModel!!.news.observe(activity!!, Observer {
            if (swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
            }

            if (viewModel!!.observableID != it.first) {
                viewModel!!.observableID = viewModel!!.observableID + 1
                viewModel!!.updateDatabase(it.second)

                if (!recentRequested) {
                    viewModel!!.news.value!!.second.subList(viewModel!!.news.value!!.second.size - viewModel!!.loadedAmount, viewModel!!.news.value!!.second.size - 1)
                        .forEach {
                            adapter!!.items.add(it)
                        }
                    adapter!!.notifyItemRangeInserted(viewModel!!.news.value!!.second.size - viewModel!!.loadedAmount, viewModel!!.news.value!!.second.size - 1)
                    //adapter?.loadItems(it.second.toMutableList())
                    //adapter?.notifyDataSetChanged()
                    //TODO show the message if all have been loaded
                } else {
                    //TODO add on the top
                    if (viewModel!!.loadedAmount > 0) {
                        viewModel!!.news.value!!.second.subList(0, viewModel!!.loadedAmount-1).asReversed().forEach {

                        }
                    } else {
                        //TODO show that nothing the most recent found
                    }


                    //TODO show the message if all have been loaded
                    recentRequested = false
                }

            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager: GridLayoutManager
        if (activity!!.getOrientation() == "PORTRAIT") {
            layoutManager = GridLayoutManager(activity, 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int): Int {
                    return when {
                        (position == 0 || position % 7 == 0) -> 2
                        else -> 1
                    }
                }
            }
        } else {
            layoutManager = GridLayoutManager(activity, 3)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int): Int {
                    return when {
                        (position == 0 || position % 7 == 0) -> 3
                        else -> 1
                    }
                }
            }
        }

        news_grid_view.layoutManager = layoutManager
        if (viewModel!!.news.value != null) {
            adapter?.loadItems(viewModel!!.news.value!!.second.toMutableList())
        } else {
            adapter?.loadItems(mutableListOf())
        }
        news_grid_view.adapter = adapter

        swipe_refresh_layout.setOnRefreshListener {
            recentRequested = true
            viewModel!!.getDataFromWebServer((viewModel!!.news.value!!.second[0]!!.date))

        }
    }


}
