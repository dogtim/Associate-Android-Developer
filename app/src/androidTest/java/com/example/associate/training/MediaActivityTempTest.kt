package com.example.associate.training

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.associate.training.media.MediaActivity
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class MediaActivityTempTest {


    @get:Rule
    val activity = ActivityScenarioRule(MediaActivity::class.java)

    @Test
    fun testSomething() {
        // your test code here
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}