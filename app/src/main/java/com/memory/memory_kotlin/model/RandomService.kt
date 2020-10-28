package com.memory.memory_kotlin.model

import android.content.Context
import android.util.ArrayMap
import androidx.preference.PreferenceManager

class RandomService {
    fun saveRandomNum(fromNum:Int,ToNum:Int,context: Context){
        //Preference登録
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("FROM_NUM",fromNum)
        editor.putInt("TO_NUM",ToNum)

        editor.apply()
    }

    fun getRandomNum(context: Context) :Map<String,Int> {
        var configNum: MutableMap<String, Int> = ArrayMap()
        //Preference取得
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        configNum["from"] = pref.getInt("FROM_NUM", 0)
        configNum["to"] = pref.getInt("TO_NUM", 100)
        return configNum
    }
}