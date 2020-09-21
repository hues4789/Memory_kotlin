package com.memory.memory_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.list_item.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = getString(R.string.login)

        auth = FirebaseAuth.getInstance()
        user = FirebaseAuth.getInstance().currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setSize(SignInButton.SIZE_STANDARD)

        sign_in_button.setOnClickListener{onLoginButtonClick(it)}

        btNoLogin.setOnClickListener{onLoginButtonClick(it)}
        SignUpButton.setOnClickListener{onLoginButtonClick(it)}
        LoginButton.setOnClickListener{onLoginButtonClick(it)}
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
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
                                Toast.makeText(
                                    baseContext, "ログインしました。",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                R.id.sign_in_button -> {
                    signIn()
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent:Intent= mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    user = auth.currentUser
                    Toast.makeText(
                        baseContext, "ログインしました。",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, TodayToDoTaskLoginActivity::class.java)
                    startActivity(intent)

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}