package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_today_to_do_task.*

class TodayToDoTaskLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_to_do_task)

        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
    }

    fun onCommonButtonClick(view: View?){
        if(view != null) {
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