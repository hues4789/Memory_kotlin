package com.memory.memory_kotlin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class TaskCreateLoginEspressoTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(TaskCreateLoginActivity::class.java)

    @Test
    fun taskCreateError(){
        Espresso.onView(ViewMatchers.withId(R.id.newTask)).perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.btCreateExe)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.newTask))
            .check(ViewAssertions.matches(CustomMatcher.withError(R.string.input_no_char_error)))
    }
}