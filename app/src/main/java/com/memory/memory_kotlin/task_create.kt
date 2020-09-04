package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class task_create : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_create)

        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
    }

    fun onCommonButtonClick(view: View?){
        when(view?.getId()) {
            R.id.btToday ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btCreate ->{
                val intent = Intent(this, task_create::class.java)
                startActivity(intent)
            }

        }
    }
}