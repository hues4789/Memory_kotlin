package com.memory.memory_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_common_button.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommonButtonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommonButtonFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommonButtonFragment.
         */
        // TODO: Rename and change types and number of parameters
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

        // Test App ID
        MobileAds.initialize(activity, "ca-app-pub-1135677094589057/6842774244")

        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // ad's lifecycle: loading, opening, closing, and so on
        adView.adListener = object:AdListener(){
            override fun onAdLoaded() {
                Log.d("debug","Code to be executed when an ad finishes loading.");
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                Log.d("debug","Code to be executed when an ad request fails.");
            }

            override fun onAdOpened() {
                Log.d("debug","Code to be executed when an ad opens an overlay that covers the screen.");
            }

            override fun onAdLeftApplication() {
                Log.d("debug","Code to be executed when the user has left the app.");
            }

            override fun onAdClosed() {
                Log.d("debug","Code to be executed when when the user is about to return to the app after tapping on an ad.");
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