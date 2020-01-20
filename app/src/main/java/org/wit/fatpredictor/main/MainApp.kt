package org.wit.fatpredictor.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.fatpredictor.models.PredictModel

class MainApp : Application(), AnkoLogger {

    var predictions = ArrayList<PredictModel>()

    override fun onCreate() {
        super.onCreate()
        info("Prediction started")
    }
}