package com.bonial.newsapp.ui


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bonial.newsapp.NewsFeedViewModel
import com.bonial.newsapp.R
import com.bonial.newsapp.databinding.FragmentNewsFeedBinding
import com.bonial.newsapp.getOrientation
import com.bonial.newsapp.model.NewsItemViewData
import com.bonial.newsapp.showToast
import kotlinx.android.synthetic.main.fragment_news_feed.*

class NewsFeedFragment : Fragment() {

    private var viewModel: NewsFeedViewModel? = null
    private var adapter: GridViewAdapter? = null
    private var recentRequested = false
    private lateinit var binding: FragmentNewsFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(NewsFeedViewModel::class.java)
        adapter = GridViewAdapter(viewModel!!) {
            goToItemFragment()
        }

        viewModel!!.news.observe(activity!!, Observer {
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
            }
            if (!it.first.alreadyReceived) {
                it.first.alreadyReceived = true

                if (!recentRequested) {
                    val tempList = mutableListOf<NewsItemViewData?>()

                    if (viewModel!!.news.value!!.second.isNotEmpty()) {
                        viewModel!!.news.value!!.second.forEach { newsItem ->
                            tempList.add(NewsItemViewData(newsItem!!, context!!.resources))
                        }
                    }

                    if (!it.first.appendingData) {
                        adapter?.items!!.clear()
                        adapter?.loadItems(tempList)
                        adapter?.notifyDataSetChanged()

                    } else {
                        var start = 0
                        if (adapter!!.itemCount > 0) {
                            start = adapter!!.itemCount
                        }
                        val end = it.second.size
                        tempList.forEach { newsItemViewData ->
                            adapter!!.items.add(newsItemViewData)
                        }
                        val handler = Handler()
                        Thread(Runnable {
                            handler.post {
                                adapter!!.notifyItemRangeInserted(start, start + end)
                            }
                        }).start()
                    }

                    // show the message if all have been loaded
                    if (viewModel!!.allEarliestLoaded) {
                        activity!!.showToast(getString(R.string.all_news_loaded))
                    }
                } else {
                    // add on the top
                    if (viewModel!!.news.value!!.second.isNotEmpty()) {
                        viewModel!!.news.value!!.second.asReversed().forEach { newsItem ->
                            adapter!!.items.add(0, NewsItemViewData(newsItem!!, context!!.resources))
                            adapter!!.notifyItemInserted(0)
                        }
                    } else {
                        // show that nothing the most recent found
                        activity!!.showToast(getString(R.string.no_recent_data_found))
                    }
                    recentRequested = false
                }
                viewModel!!.updateDatabase(viewModel!!.allNews)

            }
        })

        viewModel!!.error.observe(activity!!, Observer {
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
            }

            if (!it.first.alreadyReceived) {
               it.first.alreadyReceived = true

               when (it.second) {
                   NewsFeedViewModel.MESSAGE_NO_CONNECTION -> activity!!.showToast(getString(R.string.no_internet_connection))
                   else -> activity!!.showToast(getString(R.string.server_side_error))
               }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_feed, container, false)
        binding.lifecycleOwner = activity
        binding.viewModel = viewModel
        return binding.root
        //return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel!!.news.value == null) {
            viewModel!!.getDataFromDB()
        }

        val layoutManager: GridLayoutManager
        //https://stackoverflow.com/questions/30889066/recycleviews-span-size - configuring recycler view
        //with GridLayoutManager

        if (activity!!.getOrientation() == NewsFeedViewModel.PORTRAIT_MODE) {
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

        news_recycler_view.layoutManager = layoutManager

        if (viewModel!!.allNews.isNotEmpty()) {
            val tempList = mutableListOf<NewsItemViewData?>()
            viewModel!!.allNews.forEach{
                tempList.add(NewsItemViewData(it!!, context!!.resources))
            }
            adapter?.loadItems(tempList)
        } else {
            adapter?.loadItems(mutableListOf())
        }
        news_recycler_view.adapter = adapter

        swipe_refresh_layout.setOnRefreshListener {
            recentRequested = true
            viewModel!!.getDataFromWebServer(false)

        }
    }

    private fun goToItemFragment() {
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.navigate(R.id.feed_fr_to_item_fr)
    }


}
