package com.memory.memory_kotlin.viewmodel

import android.app.Application
import android.provider.Settings.System.getString
import android.view.View
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.contract.RandomViewContract
import kotlinx.android.synthetic.main.activity_random.*
import java.util.*

class RandomViewModel(randomView:RandomViewContract,application: Application) :AndroidViewModel(application){
    private var randomView : RandomViewContract = randomView
    private var context = getApplication<Application>().applicationContext
    var _FromNum = MutableLiveData<String>("")
    val ToNum = ObservableField<String>("")
    val RandomNum = ObservableField<String>("")

    val FromNum :LiveData<String>get() = _FromNum


    fun onRandomStartClick(view: View?){
        if(FromNum.value?.isEmpty()!! || ToNum.get()?.isEmpty()!!){
            if(FromNum.value?.isEmpty()!!) {
                randomView.noFromNumError()
            }
            if(ToNum.get()?.isEmpty()!!) {
                randomView.noToNumError()
            }
            return
        }


        val fromNum = FromNum.value.toString()
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

    fun submitText(text:String){
        _FromNum.value = text
    }
}