package org.wit.fatpredictor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.fatpredictor.R
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        info("Main Activity Started...")

        val background = object  : Thread() {
            override fun run() {
                try {
                    Thread.sleep(4000)

                    val intent = Intent(baseContext, PredictActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}