package org.wit.fatpredictor.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.fatpredictor.R

class SignUpActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        info("SignIn Activity Started...")

        btnRegester.setOnClickListener() {
            //val userName = newUsername.text.toString()
            showProgress()
            val email = newEmail.text.toString()
            val password = newPassword.text.toString()
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    hideProgress()
                    val intent = Intent(baseContext, PredictionListActivity::class.java)
                    startActivity(intent)
                } else {
                    hideProgress()
                    toast("Sign Up Failed: ${task.exception?.message}")
                }
            }
        }


        btnBackSignIn.setOnClickListener() {
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    fun showProgress() {
        progressBarSignUp.visibility = View.VISIBLE
    }

    fun hideProgress(){
        progressBarSignUp.visibility = View.GONE
    }
}