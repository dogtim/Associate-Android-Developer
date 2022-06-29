package com.example.associate.training

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RecycleViewTest {
    /**
     * Use [ActivityScenario] to create and launch the activity under test. This is a
     * replacement for [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    val activity = ActivityScenarioRule(LifecycleActivity::class.java)

    @Test
    fun scrollToItemBelowFold_checkItsText() {
        // Match the text in an item below the fold and check that it's displayed.
        val itemElementText = "WorkManager"
        Espresso.onView(ViewMatchers.withText(itemElementText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // First scroll to the position that needs to be matched and click on it.

        // First scroll to the position that needs to be matched and click on it.
        Espresso.onView(ViewMatchers.withId(R.id.tutorsRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.click()
                )
            )
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.associate.training", appContext.packageName)
    }
}