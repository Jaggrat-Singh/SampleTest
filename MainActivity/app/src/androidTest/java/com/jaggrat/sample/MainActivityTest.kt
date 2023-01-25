package com.jaggrat.sample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jaggrat.sample.news.ui.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()
    @Test
    fun setUp() {
        activityScenarioRule.scenario.moveToState(androidx.lifecycle.Lifecycle.State.STARTED)
    }


    @Test
    fun displayScreenTitle() {
        onView(allOf(instanceOf(TextView::class.java), withParent(withResourceName("action_bar"))))
            .check(matches(withText("Latest News")))

        recyclerViewIsNotEmpty(0)
        onView(withId(R.id.news_list)).check(matches(isDisplayed()))
        onView(withId(R.id.loader)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.error_tv)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        // If I had more time I could have injected retrofit with mock data to test UI content
    }

    private fun recyclerViewIsNotEmpty(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return recyclerView.adapter!!.itemCount > matcherSize
            }
        }
    }
}