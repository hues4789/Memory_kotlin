package com.memory.memory_kotlin.view

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_task_create.*
import java.util.*
import com.google.android.material.snackbar.Snackbar
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.Tasks
import java.text.SimpleDateFormat

class TaskCreateActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.create)

        setContentView(R.layout.activity_task_create)

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        btCreateExe.setOnClickListener{onCreateTaskButtonClick(it)}
    }

//task作成
private fun create(task:String,detailsTask:String){
        mRealm.executeTransaction {
            var tasks = mRealm.createObject(Tasks::class.java , UUID.randomUUID().toString())
            tasks.learnContext = task
            tasks.learn_details = detailsTask
            tasks.remember_date = formatNowDate()
            mRealm.copyToRealm(tasks)
        }
    }

    private fun onCreateTaskButtonClick(view: View?){
        if(view != null) {
            if(newTask.text.toString().isEmpty()) {
                newTask.error = getText(R.string.input_no_char_error)
                return
            }
            //DB登録
            create(newTask.text.toString(),detailsTask.text.toString())
            Snackbar.make(view,getText(R.string.added_task),Snackbar.LENGTH_SHORT)
                .setAction("戻る"){finish()}
                .setActionTextColor(Color.YELLOW)
                .show()
                }
        //キーボードを閉じる
        hideKeyboard(view)

        //入力欄を空にする
        newTask.setText("", TextView.BufferType.NORMAL)
        detailsTask.setText("", TextView.BufferType.NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    //現在日付成形
    fun formatNowDate():String{
        val formatDate = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        return formatDate.format(date).toString()
    }

    private fun hideKeyboard(view:View?) {
        if(view != null) {
            val manager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}