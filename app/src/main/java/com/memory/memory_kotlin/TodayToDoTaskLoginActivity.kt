package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_today_to_do_task.*

class TodayToDoTaskLoginActivity() : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.today)

        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_today_to_do_task)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //DB取得
        val db = FirebaseFirestore.getInstance()
        db.collection("Tasks")
                //降順
            .orderBy(TasksFirebase::created_at.name, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if(it.result != null){
                        val taskList = it.result!!.toObjects(TasksFirebase::class.java)
                        val adapter = TaskLoginRecyclerAdapter(this,this,taskList)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(applicationContext,"送信失敗", Toast.LENGTH_LONG).show()
                }
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
            //TOdo ログアウト処理
            R.id.logout_menu -> {
                auth.signOut()
                currentUser = null
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}