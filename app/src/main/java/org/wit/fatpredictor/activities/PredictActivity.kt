package org.wit.fatpredictor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_prediction.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.fatpredictor.R
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel

class PredictActivity : AppCompatActivity(), AnkoLogger {

    var predict = PredictModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)
        app = application as MainApp
        info ("Predict Activity Started...")

        btnAdd.setOnClickListener(){
            predict.weight = weight.text.toString()
            predict.height = height.text.toString()
            if (predict.weight.isNotEmpty()){
                app.predictions.add(predict.copy())
                info("add Button Pressed: $predict")
            } else {
                toast("Please Enter a title")
            }
        }
    }
}
