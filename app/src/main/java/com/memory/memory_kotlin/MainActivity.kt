package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
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

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }


}