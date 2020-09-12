package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task_create.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.login)

        auth = FirebaseAuth.getInstance()

        user = FirebaseAuth.getInstance().currentUser

        if(user != null){
            val intent = Intent(this, TodayToDoTaskLoginActivity::class.java)
            startActivity(intent)
        }

        SignUpButton.setOnClickListener {

            val emailEditText = emailEditText
            val emailText = emailEditText.text.toString()

            val passEditText = passEditText
            val passText = passEditText.text.toString()

            auth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        onLoginButtonClick(it)
                    } else {
                        Toast.makeText(
                            baseContext, "SignUp 失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        btNoLogin.setOnClickListener{onLoginButtonClick(it)}
        SignUpButton.setOnClickListener{onLoginButtonClick(it)}
        LoginButton.setOnClickListener{onLoginButtonClick(it)}
    }

    fun onLoginButtonClick(view: View?){
        if(view != null) {
            when (view.id) {
                R.id.btNoLogin -> {
                    val intent = Intent(this, TodayToDoTaskActivity::class.java)
                    startActivity(intent)
                }
                R.id.LoginButton -> {
                    //Todo funを分ける
                    val emailText = emailEditText.text.toString()

                    val passText = passEditText.text.toString()

                    if(emailText.isEmpty() || passText.isEmpty()){
                        if(emailText.isEmpty()){
                            emailEditText.error = getString(R.string.input_mail)
                        }
                        if(passText.isEmpty()){
                            passEditText.error = getString(R.string.input_pass)
                        }
                        return
                    }

                    auth.signInWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, TodayToDoTaskLoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    baseContext, "Login 失敗",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
                R.id.SignUpButton -> {
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}