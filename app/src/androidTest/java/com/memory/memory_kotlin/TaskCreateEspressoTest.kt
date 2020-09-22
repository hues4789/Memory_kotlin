package com.memory.memory_kotlin

import android.view.View
import android.widget.EditText
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_task_create.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import java.util.regex.Matcher

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

