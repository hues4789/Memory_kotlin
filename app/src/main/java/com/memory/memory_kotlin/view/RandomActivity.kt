package com.memory.memory_kotlin.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        FromNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //ここでBinding#viewmodelを参照
                if (s != null) {
                    binding.viewModel?.setFromNum(s.toString())
                }
            }
        })
        ToNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //ここでBinding#viewmodelを参照
                if (s != null) {
                    binding.viewModel?.setToNum(s.toString())
                }
            }
        })

        //Random値を設定
        binding.viewModel?.initSetNum()

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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