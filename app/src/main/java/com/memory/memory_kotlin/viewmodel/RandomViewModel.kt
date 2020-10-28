package com.memory.memory_kotlin.viewmodel

import android.app.Application
import android.provider.Settings.System.getString
import android.util.ArrayMap
import android.view.View
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.contract.RandomViewContract
import com.memory.memory_kotlin.model.RandomService
import kotlinx.android.synthetic.main.activity_random.*
import java.util.*

class RandomViewModel(randomView:RandomViewContract,application: Application) :AndroidViewModel(application){
    private var randomView : RandomViewContract = randomView
    private var context = getApplication<Application>().applicationContext
    var _FromNum = MutableLiveData<String>("")
    val _ToNum = MutableLiveData<String>("")
    val RandomNum = ObservableField<String>("")

    val FromNum :LiveData<String>get() = _FromNum
    val ToNum :LiveData<String>get() = _ToNum

    var randomService = RandomService()


    fun onRandomStartClick(view: View?){
        if(FromNum.value?.isEmpty()!! || ToNum.value?.isEmpty()!!){
            if(FromNum.value?.isEmpty()!!) {
                randomView.noFromNumError()
            }
            if(ToNum.value?.isEmpty()!!) {
                randomView.noToNumError()
            }
            return
        }


        val fromNum = FromNum.value.toString()
        val toNum = ToNum.value.toString()

        if(Integer.parseInt(fromNum) >= Integer.parseInt(toNum) ){
            randomView.bigSmallCheckError()
            return
        }

        randomView.removeError()

        //preference登録
        randomService.saveRandomNum(Integer.parseInt(fromNum),Integer.parseInt(toNum),context)

        //ランダム数を作成
        val random = Random()
        val randomNum = random.nextInt(Integer.parseInt(toNum) - Integer.parseInt(fromNum)+1) + Integer.parseInt(fromNum)

        //ランダム数を出力
        RandomNum.set(randomNum.toString())

        randomView.hideKeyboard(view)
    }

    fun initSetNum(){
        val randomConfigNum :Map<String,Int> = randomService.getRandomNum(context)
        val fromNum = randomConfigNum["from"]
        val toNum = randomConfigNum["to"]

        //取得した値を設定
        _FromNum.value = fromNum.toString()
        _ToNum.value = toNum.toString()
    }

    fun setFromNum(fromNum:String){
        _FromNum.value = fromNum
    }
    fun setToNum(toNum:String){
        _ToNum.value = toNum
    }
}