package com.example.associate.training

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.associate.training.media.MediaActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MediaActivity::class.java)

    @Test
    fun testEncoding() {

        val startTime = System.currentTimeMillis()
        var clicked = false
        val progressIndicator = onView(withId(R.id.progress_indicator))
        val button = onView(withId(R.id.export_to_video))
        while (!clicked && System.currentTimeMillis() - startTime < 10000) {
            try {
                button.check(matches(allOf(isClickable(), isDisplayed())))
                button.perform(click())
                // We need to wait for the progress indicator to be displayed because sometimes performing click is not working
                progressIndicator.check(matches(object : TypeSafeMatcher<View>() {
                    override fun describeTo(description: Description?) {
                        description?.appendText("Indeterminate progress bar")
                    }

                    override fun matchesSafely(item: View?): Boolean {
                        return item is CircularProgressIndicator && item.isIndeterminate
                    }
                }))
                clicked = true
            } catch (e: Throwable) {
                Thread.sleep(100)
            }
        }

        Thread.sleep(10000) // Wait for encoding to complete (adjust time as needed)

        // Verify that the "Complete" Toast is displayed
        onView(withId(R.id.content)).check(matches(withText("Complete")))

    }

}