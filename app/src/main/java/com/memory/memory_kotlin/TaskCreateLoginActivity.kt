package com.memory.memory_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task_create.*
import kotlinx.android.synthetic.main.activity_today_to_do_task.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaskCreateLoginActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = getString(R.string.create)

        setContentView(R.layout.activity_task_create)

        btCreateExe.setOnClickListener{onCreateButtonClick(it)}
    }

    fun onCreateButtonClick(view: View?) {
        if (view != null) {
            if(newTask.text.toString().isEmpty()){
                newTask.error = getString(R.string.input_no_char_error)
                return
            }

            val task = newTask.text.toString()
            val detail = detailsTask.text.toString()

            val db = FirebaseFirestore.getInstance()
            val user = TasksFirebase(task,detail,auth.uid.toString(),formatNowDate())
            db.collection("Tasks")
                .document()
                .set(user)
                .addOnSuccessListener {Toast.makeText(applicationContext,getText(R.string.added_task),Toast.LENGTH_LONG).show()

                    //キーボードを閉じる
                    hideKeyboard(view)

                    //入力欄を空にする
                    newTask.setText("", TextView.BufferType.NORMAL)
                    detailsTask.setText("", TextView.BufferType.NORMAL)}
                .addOnFailureListener { Toast.makeText(applicationContext,"失敗",Toast.LENGTH_LONG).show() }
        }
    }

    //メニュー処理
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.task_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                auth.signOut()
                currentUser = null
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //現在日付成形
    fun formatNowDate():String{
        val formatDate = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
       return formatDate.format(date).toString()
    }

    //キーボードを閉じる
    private fun hideKeyboard(view:View?) {
        if(view != null) {
            val manager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}