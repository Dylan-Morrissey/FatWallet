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
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictionFireStore

class LoginActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: PredictionFireStore? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        app = application as MainApp
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity Started...")
        progressBar.visibility = GONE
        if (app.predictions is PredictionFireStore) {
            fireStore = app.predictions as PredictionFireStore
        }

        /*
        btnForgotPassword.setOnClickListener() {
            val intent = Intent(baseContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        */

        btnSignUp.setOnClickListener() {
            val intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener() {
            showProgress()
            if(loginEmail.text.toString().isEmpty() || loginPassword.text.toString().isEmpty()){
                toast("Please fill in required fields.")
                hideProgress()
            } else{
                val email = loginEmail.text.toString()
                val password = loginPassword.text.toString()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                    if (task.isSuccessful){
                        if (fireStore != null){
                            fireStore!!.fetchPredictions {
                                hideProgress()
                                val intent = Intent(baseContext, PredictionListActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            hideProgress()
                            val intent = Intent(baseContext, PredictionListActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        hideProgress()
                        toast("Sign In Failed: ${task.exception?.message}")
                    }
                }
            }

    }
    }

    fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    fun hideProgress(){
        progressBar.visibility = GONE
    }
}