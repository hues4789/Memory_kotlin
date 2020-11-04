package com.memory.memory_kotlin.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TaskCreateViewModel {

    val _newTask = MutableLiveData<String>("")
    val _detailsTask= MutableLiveData<String>("")

    val newTask : LiveData<String>
        get() = _newTask
    val detailsTask : LiveData<String>
        get() = _detailsTask

    fun onCreateTaskButtonClick(view: View){
        return
    }

}