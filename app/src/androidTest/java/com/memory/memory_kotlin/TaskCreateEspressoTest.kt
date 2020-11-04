package com.memory.memory_kotlin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.memory.memory_kotlin.view.TaskCreateActivity
import org.junit.Rule
import org.junit.Test

class TaskCreateEspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TaskCreateActivity::class.java)

    @Test
    fun taskCreateError(){
        onView(withId(R.id.newTask)).perform(replaceText(""))
        onView(withId(R.id.btCreateExe)).perform(click())
        onView(withId(R.id.newTask)).check(matches(CustomMatcher.withError(R.string.input_no_char_error)))
    }
}

