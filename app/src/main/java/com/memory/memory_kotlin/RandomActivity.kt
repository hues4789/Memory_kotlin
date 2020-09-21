package com.memory.memory_kotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import androidx.preference.PreferenceManager

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_random.*
import kotlinx.android.synthetic.main.fragment_common_button.*
import java.util.*

class RandomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)

        supportActionBar?.title = getString(R.string.random)

        //Random値を設定
        init()

        //Random数生成
        btRandomStart.setOnClickListener{onRandomStartClick(it)}

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun init(){
        val randomConfigNum :Map<String,Int> = getRandomNum()
        val fromNum = randomConfigNum["from"]
        val toNum = randomConfigNum["to"]

        //取得した値を設定
        FromNum.setText(fromNum.toString(), TextView.BufferType.NORMAL)
        ToNum.setText(toNum.toString(), TextView.BufferType.NORMAL)

    }


    fun onRandomStartClick(view: View?){
        if(FromNum.text.isEmpty()|| ToNum.text.isEmpty()){
            if(FromNum.text.isEmpty()) {
                FromNum.error = "入力して下さい。"
            }
            if(ToNum.text.isEmpty()) {
                ToNum.error = "入力して下さい。"
            }
            return
        }


        val fromNum = FromNum.text.toString()
        val toNum = ToNum.text.toString()

        if(Integer.parseInt(fromNum) >= Integer.parseInt(toNum) ){
            FromNum.error = "大小が正しくありません。。"
            ToNum.error = "大小が正しくありません。"
            return
        }

        FromNum.error = null
        ToNum.error = null

        //preference登録
        saveRandomNum(Integer.parseInt(fromNum),Integer.parseInt(toNum))

       //ランダム数を作成
        val random = Random()
        val randomNum = random.nextInt(Integer.parseInt(toNum) - Integer.parseInt(fromNum)+1) + Integer.parseInt(fromNum)

        //ランダム数を出力
        RandomNum.text = randomNum.toString()

        hideKeyboard(view)
    }
    private fun saveRandomNum(fromNum:Int,ToNum:Int){
        //Preference登録
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("FROM_NUM",fromNum)
        editor.putInt("TO_NUM",ToNum)

        editor.apply()
    }
    private fun getRandomNum() :Map<String,Int> {
        var configNum: MutableMap<String, Int> = ArrayMap()
        //Preference取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        configNum["from"] = pref.getInt("FROM_NUM", 0)
        configNum["to"] = pref.getInt("TO_NUM", 100)
        return configNum
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