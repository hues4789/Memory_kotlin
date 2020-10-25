package com.memory.memory_kotlin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ReplaceTextAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.memory.memory_kotlin.view.RandomActivity
import org.junit.Rule
import org.junit.Test

class RandomEspressoTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RandomActivity::class.java)

    @Test
    fun fromNumEmptyError(){
        Espresso.onView(ViewMatchers.withId(R.id.FromNum)).perform(ReplaceTextAction(""))
        Espresso.onView(ViewMatchers.withId(R.id.btRandomStart)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.FromNum)).check(ViewAssertions.matches(CustomMatcher.withError(R.string.please_input)))
    }
    @Test
    fun toNumEmptyError(){
        Espresso.onView(ViewMatchers.withId(R.id.ToNum)).perform(ReplaceTextAction(""))
        Espresso.onView(ViewMatchers.withId(R.id.btRandomStart)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.ToNum)).check(ViewAssertions.matches(CustomMatcher.withError(R.string.please_input)))
    }
    @Test
    fun compareNumError(){
        Espresso.onView(ViewMatchers.withId(R.id.FromNum)).perform(ReplaceTextAction("300"))
        Espresso.onView(ViewMatchers.withId(R.id.ToNum)).perform(ReplaceTextAction("20"))
        Espresso.onView(ViewMatchers.withId(R.id.btRandomStart)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.ToNum)).check(ViewAssertions.matches(CustomMatcher.withError(R.string.big_small_check)))
    }
}