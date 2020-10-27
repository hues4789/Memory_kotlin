package com.memory.memory_kotlin.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.view.MenuItem
import androidx.preference.PreferenceManager

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.contract.RandomViewContract
import com.memory.memory_kotlin.databinding.ActivityRandomBinding
import com.memory.memory_kotlin.viewmodel.RandomViewModel
import kotlinx.android.synthetic.main.activity_random.*

class RandomActivity : AppCompatActivity(), RandomViewContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding :ActivityRandomBinding= DataBindingUtil.setContentView(this, R.layout.activity_random)
        val randomViewModel = RandomViewModel(this as RandomViewContract,this.application)
        binding.viewModel = randomViewModel
        binding.lifecycleOwner = this

        supportActionBar?.title = getString(R.string.random)

        btRandomStart.setOnClickListener {
            val text = FromNum.toString()
            randomViewModel.submitText(text)
        }

        //Random値を設定
        init()

        //Random数生成
/*        btRandomStart.setOnClickListener{onRandomStartClick(it)}*/

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btRandomStart.setOnClickListener {
            val fromNumVal = FromNum.toString()
            randomViewModel.submitText(fromNumVal)
        }

    }

    fun init(){
        val randomConfigNum :Map<String,Int> = getRandomNum()
        val fromNum = randomConfigNum["from"]
        val toNum = randomConfigNum["to"]

        //取得した値を設定
        FromNum.setText(fromNum.toString(), TextView.BufferType.NORMAL)
        ToNum.setText(toNum.toString(), TextView.BufferType.NORMAL)

    }




/*    fun onRandomStartClick(view: View?){
        if(FromNum.text.isEmpty()|| ToNum.text.isEmpty()){
            if(FromNum.text.isEmpty()) {
                FromNum.error = getString(R.string.please_input)
            }
            if(ToNum.text.isEmpty()) {
                ToNum.error = getString(R.string.please_input)
            }
            return
        }


        val fromNum = FromNum.text.toString()
        val toNum = ToNum.text.toString()

        if(Integer.parseInt(fromNum) >= Integer.parseInt(toNum) ){
            FromNum.error = getString(R.string.big_small_check)
            ToNum.error = getString(R.string.big_small_check)
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
    }*/
    private fun getRandomNum() :Map<String,Int> {
        var configNum: MutableMap<String, Int> = ArrayMap()
        //Preference取得
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        configNum["from"] = pref.getInt("FROM_NUM", 0)
        configNum["to"] = pref.getInt("TO_NUM", 100)
        return configNum
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

    override  fun noFromNumError(){
        FromNum.error = getString(R.string.please_input)
    }

    override fun noToNumError(){
        ToNum.error = getString(R.string.please_input)
    }

    override fun bigSmallCheckError(){
        FromNum.error = getString(R.string.big_small_check)
        ToNum.error = getString(R.string.big_small_check)
    }
    override  fun removeError() {
        FromNum.error = null
        ToNum.error = null
    }

    override fun hideKeyboard(view:View?) {
        if (view != null) {
            val manager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}