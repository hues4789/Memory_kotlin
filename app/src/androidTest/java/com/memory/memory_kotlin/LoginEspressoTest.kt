package com.memory.memory_kotlin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ReplaceTextAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class LoginEspressoTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun emailEmptyError(){
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ReplaceTextAction(""))
        Espresso.onView(ViewMatchers.withId(R.id.LoginButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).check(ViewAssertions.matches(CustomMatcher.withError(R.string.input_mail)))
    }
    @Test
    fun passwordEmptyError(){
        Espresso.onView(ViewMatchers.withId(R.id.passEditText)).perform(ReplaceTextAction(""))
        Espresso.onView(ViewMatchers.withId(R.id.LoginButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.passEditText)).check(ViewAssertions.matches(CustomMatcher.withError(R.string.input_pass)))
    }
}