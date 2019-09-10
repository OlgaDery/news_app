package com.bonial.newsapp

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SingleItemFragmentTest {

    private var activity: MainActivity? = null

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, true)


    @Before
    fun setUp() {
        activity = activityRule.activity
        onView(withId(R.id.news_grid_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
            .perform(ViewActions.click())
    }

    @Test
    fun fragmentFeed_recyclerView_itemToItemFragment() {

        onView(withId(R.id.item_fragment_card_view)).check(matches(allOf(isDisplayed(),
            withChild(withId(R.id.scroll_view)))))

        onView(withId(R.id.wrapper_layout)).check(matches(allOf(isDisplayed(),
            instanceOf(LinearLayout::class.java), withChild(withId(R.id.title)),
            withChild(withId(R.id.img_picture)), withChild(withId(R.id.descript)),
            withChild(withId(R.id.content)), withChild(withId(R.id.link_text_view))
        )))

        onView(withId(R.id.title)).check(matches(allOf(isDisplayed(), instanceOf(TextView::class.java))))
        onView(withId(R.id.img_picture)).check(matches(allOf(isDisplayed(), instanceOf(ImageView::class.java))))
        onView(withId(R.id.descript)).check(matches(allOf(isDisplayed(), instanceOf(TextView::class.java))))
        onView(withId(R.id.content)).check(matches(allOf(isDisplayed(), instanceOf(TextView::class.java))))
        onView(withId(R.id.link_text_view)).check(matches(allOf(isClickable(), hasTextColor(R.color.colorBlue))))
    }

    @Test
    fun itemFragment_navigateToExternalSite() {
        Thread.sleep(500)
        onView(withId(R.id.scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.link_text_view)).perform(ViewActions.click())
    }
}