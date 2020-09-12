package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_today_to_do_task.*
import org.w3c.dom.Text

class TodayToDoTaskActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.today)

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
    }

    fun readList(): RealmResults<Tasks> {
        return mRealm.where(Tasks::class.java).findAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}