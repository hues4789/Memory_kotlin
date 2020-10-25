package com.memory.memory_kotlin.contract

import android.view.View

public interface  RandomViewContract {
    fun noFromNumError(){}
    fun noToNumError(){}
    fun bigSmallCheckError(){}
    fun removeError() {}
    fun hideKeyboard(view: View?){}
}