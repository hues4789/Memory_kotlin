package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
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
import kotlinx.android.synthetic.main.list_item.view.*

class HistoryLoginActivity : AppCompatActivity(),TaskLoginRecyclerViewHolder.ItemClickListener  {

    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private var taskList:MutableList<TasksFirebase> = ArrayList<TasksFirebase>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.histories)

        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser

        setContentView(R.layout.activity_today_to_do_task)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val loginId = auth.uid.toString()

        //DB取得
        val db = FirebaseFirestore.getInstance()
        db.collection("Tasks")
            .whereEqualTo("login_user",loginId)
            //降順
            .orderBy(TasksFirebase::created_at.name, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if(it.result != null){
                        taskList = it.result!!.toObjects(TasksFirebase::class.java)
                        val adapter = TaskLoginRecyclerAdapter(this,this,taskList)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = adapter
                    }
                } else {
                    Toast.makeText(applicationContext,"今日のタスクはありません。", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onItemClick(view: View, position:Int){
        if(view.create_dt.text.isEmpty()){
            view.task.text = taskList[position].learnContext

            val createDate = DateFormat.format("yyyy/MM/dd",taskList[position].created_at)
            view.create_dt.text = createDate
        }else {
            view.task.text = taskList[position].learn_details
            view.create_dt.text = ""
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
            else -> return super.onOptionsItemSelected(item)
        }
    }
}