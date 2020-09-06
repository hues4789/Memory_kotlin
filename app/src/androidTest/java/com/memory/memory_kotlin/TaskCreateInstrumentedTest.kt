package com.memory.memory_kotlin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_task_create.*
import org.junit.Rule
import org.junit.Test

class TaskCreateInstrumentedTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TaskCreateActivity::class.java)

    @Test
    fun taskCreateError(){
        onView(withId(R.id.newTask)).perform(replaceText(""))
        onView(withId(R.id.btCreateExe)).perform(click())
        onView(withId(R.id.errorText)).check(matches(withText(R.string.input_no_char_error)))
    }
}