package com.bonial.newsapp

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import org.junit.Assert.*
import org.junit.Rule
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NewsFeedFragmentTest {
    var appContext: Context? = null
    var activity: MainActivity? = null

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, true)


    @Before
    fun setUp() {
        //appContext = InstrumentationRegistry.
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

    @Test
    fun fragment_feed_recyclerView_swipeRefresh_isClickable() {

    }

    //TODO check orientation and amount of columns

    @Test
    fun fragmentFeed_recyclerView_itemToItemFragment() {
        onView(withId(R.id.news_grid_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(1))
            .perform(click())
        onView(withId(R.id.item_fragment_card_view)).check(matches(allOf(isDisplayed(), withSubstring("Trevor"))))
//        onView(withId(R.id.address_text_view)).check(matches(allOf(isDisplayed(), withSubstring("90210"))))
//        onView(withId(R.id.phone_numbers_recycler_view))
//            .scrollToPosition(0)
//            .check(matches(itemAtPosition(0, allOf(hasDescendant(withText(R.string.other)), hasDescendant(withText("676-987-0987"))))))
//        onView(withId(R.id.phone_numbers_recycler_view))
//            .scrollToPosition(2)
//            .check(matches(itemAtPosition(2, allOf(hasDescendant(withText(R.string.cell)), hasDescendant(withText("403-555-5555"))))))
    }

//    @Test
    fun editPersonalInfo_updatedSuccessfully() {
//        onToolbarItem(withText(R.string.edit)).click()
//        onView(withId(R.id.address_line_1_edit_text)).perform(replaceText("156 Turner Close S.E."))
//        onView(withId(R.id.contact_info_recycler_view)).scrollToPosition(1)
//        onView(allOf(withId(R.id.edit_text), isDescendantOfA(withId(R.id.contact_info_recycler_view), 1)))
//            .perform(replaceText("403-555-5555"))
    }

//    @Test
    fun emailAvantiSupportAndReturnToLogin_returnsToLoginPage() {
//        onView(Matchers.allOf(withContentDescription(R.string.support), isDescendantOfA(withId(R.id.toolbar)), isDisplayed())).perform(ViewActions.click())
//        onView(allOf(withId(R.id.email_avanti_button), isDisplayed())).perform((ViewActions.click()))
//        onView(allOf(withContentDescription(R.string.abc_action_bar_up_description), isDisplayed())).perform(
//            ViewActions.click())
    }

}
