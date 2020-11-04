package com.memory.memory_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.memory.memory_kotlin.view.RandomActivity
import com.memory.memory_kotlin.view.TaskCreateActivity
import kotlinx.android.synthetic.main.fragment_common_button.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CommonButtonFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_button, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommonButtonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onStart(){
        super.onStart()
        btToday.setOnClickListener{onCommonButtonClick(it)}
        btCreate.setOnClickListener{onCommonButtonClick(it)}
        btHistory.setOnClickListener{onCommonButtonClick(it)}
        btRandom.setOnClickListener{onCommonButtonClick(it)}

        // App ID
        MobileAds.initialize(activity){}

        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object:AdListener(){
            override fun onAdLoaded() {
            }

            override fun onAdFailedToLoad(errorCode : Int) {
            }

            override fun onAdOpened() {
            }

            override fun onAdLeftApplication() {
            }

            override fun onAdClosed() {
            }
        }

    }

    private fun onCommonButtonClick(view: View?){
        if(view != null) {
            if(activity != null) {
                //login画面の場合
                if (activity!!.localClassName == "TodayToDoTaskLoginActivity" || activity!!.localClassName == "TaskCreateLoginActivity" || activity!!.localClassName == "HistoryLoginActivity" ||activity!!.localClassName == "RandomLoginActivity") {
                    when (view.id) {
                        R.id.btToday -> {
                            val intent = Intent(activity, TodayToDoTaskLoginActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btCreate -> {
                            val intent = Intent(activity, TaskCreateLoginActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btHistory -> {
                            val intent = Intent(activity, HistoryLoginActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btRandom -> {
                            val intent = Intent(activity, RandomLoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                //login画面以外の場合
                } else {
                    when (view.id) {
                        R.id.btToday -> {
                            val intent = Intent(activity, TodayToDoTaskActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btCreate -> {
                            val intent = Intent(activity, TaskCreateActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btHistory -> {
                            val intent = Intent(activity, HistoryActivity::class.java)
                            startActivity(intent)
                        }
                        R.id.btRandom -> {
                            val intent = Intent(activity, RandomActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}