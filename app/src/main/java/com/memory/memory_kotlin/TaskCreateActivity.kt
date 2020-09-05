package com.memory.memory_kotlin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.btCreate
import kotlinx.android.synthetic.main.activity_main.btToday
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

    fun onCommonButtonClick(view: View?){
        if(view != null) {
            when (view.id) {
                R.id.btToday -> {
                    val intent = Intent(this, MainActivity::class.java)
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
    fun create(task:String){
        mRealm.executeTransaction {
            var tasks = mRealm.createObject(Tasks::class.java , UUID.randomUUID().toString())
            tasks.learnContext = task
            mRealm.copyToRealm(tasks)
        }
    }

    fun onCreateTaskButtonClick(view: View?){
        if(view != null) {
            //DB登録
            create(newTask.text.toString())

            Snackbar.make(view,"追加しました",Snackbar.LENGTH_SHORT)
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