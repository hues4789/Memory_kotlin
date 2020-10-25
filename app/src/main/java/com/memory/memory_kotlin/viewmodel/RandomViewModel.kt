package com.memory.memory_kotlin.viewmodel

import android.app.Application
import android.provider.Settings.System.getString
import android.view.View
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.contract.RandomViewContract
import kotlinx.android.synthetic.main.activity_random.*
import java.util.*

class RandomViewModel(randomView:RandomViewContract,application: Application) :AndroidViewModel(application){
    private var randomView : RandomViewContract = randomView
    private var context = getApplication<Application>().applicationContext
    val FromNum = ObservableField<String>("")
    val ToNum = ObservableField<String>("")
    val RandomNum = ObservableField<String>("")


    fun onRandomStartClick(view: View?){
        if(FromNum.get()?.isEmpty()!! || ToNum.get()?.isEmpty()!!){
            if(FromNum.get()?.isEmpty()!!) {
                randomView.noFromNumError()
            }
            if(ToNum.get()?.isEmpty()!!) {
                randomView.noToNumError()
            }
            return
        }


        val fromNum = FromNum.get().toString()
        val toNum = ToNum.get().toString()

        if(Integer.parseInt(fromNum) >= Integer.parseInt(toNum) ){
            randomView.bigSmallCheckError()
            return
        }

        randomView.removeError()

        //preference登録
        saveRandomNum(Integer.parseInt(fromNum),Integer.parseInt(toNum))

        //ランダム数を作成
        val random = Random()
        val randomNum = random.nextInt(Integer.parseInt(toNum) - Integer.parseInt(fromNum)+1) + Integer.parseInt(fromNum)

        //ランダム数を出力
        RandomNum.set(randomNum.toString())

        randomView.hideKeyboard(view)
    }

    private fun saveRandomNum(fromNum:Int,ToNum:Int){
        //Preference登録
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("FROM_NUM",fromNum)
        editor.putInt("TO_NUM",ToNum)

        editor.apply()
    }
}