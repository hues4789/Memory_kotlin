package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.emailEditText
import kotlinx.android.synthetic.main.activity_main.passEditText
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.title = getString(R.string.signup)

        auth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener{onLoginButtonClick(it)}
    }

    fun onLoginButtonClick(view: View?){
        if(view != null) {
            val emailText = emailEditText.text.toString()

            val passText = passEditText.text.toString()
            val confirmPassText = confirmPassEditText.text.toString()

            //入力エラー
            if(emailText.isEmpty() || passText.isEmpty()|| !emailText.equals(passText)){
                if(emailText.isEmpty()){
                    emailEditText.setError(getString(R.string.input_mail))
                }
                if(passText.isEmpty()){
                    passEditText.setError(getString(R.string.input_pass))
                }
                if(confirmPassText.isEmpty()){
                    confirmPassEditText.setError(getString(R.string.input_pass))
                }
                if(!confirmPassText.equals(passText)){
                    confirmPassEditText.setError(getString(R.string.dif_pass))
                }
                return
            }

            auth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, TodayToDoTaskActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext, "SignUp 失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}