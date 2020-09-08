package com.memory.memory_kotlin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_task_create.*
import java.util.*
import com.google.android.material.snackbar.Snackbar

class TaskCreateActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_create)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
        btCreateExe.setOnClickListener{onCreateTaskButtonClick(it)}
    }

    private fun onCommonButtonClick(view: View?){
        if(view != null) {
            when (view.id) {
                R.id.btToday -> {
                    val intent = Intent(this, TodayToDoTaskActivity::class.java)
                    startActivity(intent)
                }
                R.id.btCreate -> {
                    val intent = Intent(this, TaskCreateActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

//task作成
private fun create(task:String){
        mRealm.executeTransaction {
            var tasks = mRealm.createObject(Tasks::class.java , UUID.randomUUID().toString())
            tasks.learnContext = task
            mRealm.copyToRealm(tasks)
        }
    }

    private fun onCreateTaskButtonClick(view: View?){
        if(view != null) {
            if(newTask.text.toString().isEmpty()) {
                errorText.text = getText(R.string.input_no_char_error)
                return
            }
            //DB登録
            create(newTask.text.toString())

            Snackbar.make(view,getText(R.string.added_task),Snackbar.LENGTH_SHORT)
                .setAction("戻る"){finish()}
                .setActionTextColor(Color.YELLOW)
                .show()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}