package com.memory.memory_kotlin

import android.view.View
import android.widget.EditText
import androidx.test.espresso.matcher.BoundedMatcher
import org.junit.runner.Description

class CustomMatcher {
    class withError(private val expectedId :Int)
        : BoundedMatcher<View, EditText>(EditText::class.java) {
        private var expectedText: String? = null

        public override fun matchesSafely(view: EditText): Boolean {
            if(view is EditText) {
                expectedText = view.resources.getString(expectedId)
                val actualText: String = view.error.toString()
                return actualText != null && actualText == expectedText
            }
            return false
        }

        override fun describeTo(description: org.hamcrest.Description?) {
        }
    }
}