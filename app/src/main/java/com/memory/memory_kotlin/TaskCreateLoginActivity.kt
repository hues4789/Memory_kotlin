package com.memory.memory_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_today_to_do_task.*

class TaskCreateLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_create)
        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
    }

    fun onCommonButtonClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btToday -> {
                    val intent = Intent(this, TodayToDoTaskLoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.btCreate -> {
                    val intent = Intent(this, TaskCreateLoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}