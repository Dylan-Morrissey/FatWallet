package org.wit.fatpredictor.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.fatpredictor.R

class LoginActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        var auth:FirebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity Started...")
        progressBar.visibility = GONE

        /*
        btnForgotPassword.setOnClickListener() {
            val intent = Intent(baseContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        */
        /*
        btnSignUp.setOnClickListener() {
            val intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }
        */
        btnSignIn.setOnClickListener() {
            showProgress()
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    val intent = Intent(baseContext, PredictionListActivity::class.java)
                    startActivity(intent)
                } else {
                    toast("Sign In Failed")
                }
            }
            hideProgress()
        }
    }

    fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    fun hideProgress(){
        progressBar.visibility = GONE
    }
}