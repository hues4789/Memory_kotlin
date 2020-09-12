package com.memory.memory_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task_create.*
import kotlinx.android.synthetic.main.activity_today_to_do_task.*

class TaskCreateLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.create)

        setContentView(R.layout.activity_task_create)

        btCreateExe.setOnClickListener{onCommonButtonClick(it)}
    }

    fun onCommonButtonClick(view: View?) {
        if (view != null) {
            when (view.id) {

                R.id.btCreateExe -> {
                    if(newTask.text.toString().isEmpty()){
                        newTask.error = getString(R.string.input_no_char_error)
                        return
                    }

                    val task = newTask.text.toString()

                    val db = FirebaseFirestore.getInstance()
                    val user = TasksFirebase(task)
                    db.collection("Tasks")
                        //document->プライマリーキーみたいな感じ
                        .document()
                        .set(user)
                        .addOnSuccessListener {Toast.makeText(applicationContext,"送信完了",Toast.LENGTH_LONG).show()}
                        .addOnFailureListener { Toast.makeText(applicationContext,"送信失敗",Toast.LENGTH_LONG).show() }
                }
            }
        }
    }
}