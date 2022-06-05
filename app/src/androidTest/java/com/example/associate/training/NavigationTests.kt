package com.example.associate.training

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.associate.training.word.LetterListFragment
import com.example.associate.training.word.WordActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTests {
  /*
  * Testing Navigation Components differs from testing regular navigation.
  * When we test regular navigation, we trigger the navigation to execute on the device or emulator.
  * When we test Navigation Components, we don't actually make the device or emulator visibly navigate.
  * Instead, we force the navigation controller to navigate without actually changing what is seen on the device or emulator,
  * and then we check to make sure that the navigation controller arrived at the correct destination.
  * */
    @get:Rule
    val activity = ActivityScenarioRule(WordActivity::class.java)

    @Test
    fun navigate_to_words_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId =
        R.style.Theme_AssociateAndroidDeveloper)

        letterListScenario.onFragment { fragment ->

            navController.setGraph(R.navigation.nav_word_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}