package com.memory.memory_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodayToDoTaskActivity : AppCompatActivity(), Adapter.ViewHolder.ItemClickListener{

    private lateinit var mRealm : Realm

    var read: OrderedRealmCollection<Tasks> = RealmList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.today)

        setContentView(R.layout.activity_today_to_do_task)

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        read = readList()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //RecyclerViewのAdapter、layoutなどの設定
        val adapter = Adapter(this,read,this, true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun readList(): RealmResults<Tasks> {

        //振り返り日付間隔
        val beforeDays :MutableList<Int> = arrayListOf(-1,-4,-11,-25,-56)
        //振り返り日付生成
        val remindDate = dateFormat(beforeDays)

        return mRealm.where(Tasks::class.java)
            .equalTo("remember_date",remindDate[0])
            .or()
            .equalTo("remember_date",remindDate[1])
            .or()
            .equalTo("remember_date",remindDate[2])
            .or()
            .equalTo("remember_date",remindDate[3])
            .or()
            .equalTo("remember_date",remindDate[4])
            .findAll()
            .sort("created_at", Sort.DESCENDING)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    override fun onItemClick(view: View, position: Int) {
        if(view.create_dt.text.isEmpty()){
            view.task.text = read[position].learnContext
            val createDate = DateFormat.format("yyyy/MM/dd",read[position].created_at)
            view.create_dt.text = createDate
        }else {
            view.task.text = read[position].learn_details
            view.create_dt.text = ""
        }
    }

    //振り返り日付生成
    fun dateFormat(beforeDays :MutableList<Int>) :MutableList<String>{

        val remindDateList:MutableList<String> = ArrayList()

        val formatDate = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date

        beforeDays.forEach{ beforeDay ->
            calendar.add(Calendar.DATE, beforeDay)
            val remindDate = calendar.time
            remindDateList.add(formatDate.format(remindDate).toString())
        }

        return remindDateList
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