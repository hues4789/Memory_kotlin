package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_today_to_do_task.*

class TodayToDoTaskActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_to_do_task)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        val read = readList()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //RecyclerViewのAdapter、layouｔなどの設定
        val adapter = Adapter(this, read, true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
    }

    fun onCommonButtonClick(view: View?){
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

    fun readList(): RealmResults<Tasks> {
        return mRealm.where(Tasks::class.java).findAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}