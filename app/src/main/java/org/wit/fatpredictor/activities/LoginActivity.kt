package org.wit.fatpredictor.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.fatpredictor.R

class LoginActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity Started...")

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
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            if(email == "" || password == "") {
                toast("Please enter email and password.")
            } else{
                val intent = Intent(baseContext, PredictionListActivity::class.java)
                startActivity(intent)
            }

        }
    }
}