package com.bonial.newsapp

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class NewsFeedFragmentTest {
    private var activity: MainActivity? = null

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, true)


    @Before
    fun setUp() {
        activity = activityRule.activity
    }

    @Test
    fun fragmentFeed_swipeRefreshLayout_isDisplayed() {
        onView(withId(R.id.swipe_refresh_layout)).check(matches(allOf(isEnabled(), instanceOf(SwipeRefreshLayout::class.java))))
        onView(withId(R.id.news_grid_view)).check(matches(allOf(isDisplayed(), instanceOf(RecyclerView::class.java), isDescendantOfA(withId(R.id.swipe_refresh_layout)))))
    }

   @Test
    fun fragmentFeed_recyclerView_gridItemBig_allElementsDisplayed() {
       onView(withId(R.id.news_grid_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(0))
           .check(matches(
               hasDescendant(allOf(withId(R.id.wrapper_big_card_view), isClickable(), instanceOf(CardView::class.java),
               hasDescendant(allOf(withId(R.id.item_image), instanceOf(ImageView::class.java), isDisplayed(), withContentDescription(R.string.image_content_descriptor))),
               hasDescendant(allOf(withId(R.id.title_text_view), instanceOf(TextView::class.java), isDisplayed())),
               hasDescendant(allOf(withId(R.id.content_text_view), instanceOf(TextView::class.java), isDisplayed())),
               hasDescendant(allOf(withId(R.id.provider_name_text_view), instanceOf(TextView::class.java), isDisplayed())),
               hasDescendant(allOf(withId(R.id.posted_time_view), instanceOf(TextView::class.java), isDisplayed())
               )))))
    }

   @Test
    fun fragmentFeed_recyclerView_gridItemSmall_allElementsDisplayed() {
       onView(withId(R.id.news_grid_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(1))
           .check(matches(
                    hasDescendant(allOf(withId(R.id.item_card_view_small), isClickable(), instanceOf(CardView::class.java),
                    hasDescendant(allOf(withId(R.id.item_image), instanceOf(ImageView::class.java), isDisplayed(), withContentDescription(R.string.image_content_descriptor))),
                    hasDescendant(allOf(withId(R.id.title_text_view), instanceOf(TextView::class.java), isDisplayed())),
                    hasDescendant(allOf(withId(R.id.provider_name_text_view), instanceOf(TextView::class.java), isDisplayed())),
                    hasDescendant(allOf(withId(R.id.posted_time_view), instanceOf(TextView::class.java), isDisplayed())
                   )))))
    }

}
