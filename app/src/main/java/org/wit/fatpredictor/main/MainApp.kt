package org.wit.fatpredictor.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.fatpredictor.models.PredictModel
import org.wit.fatpredictor.models.PredictionMemStore

class MainApp : Application(), AnkoLogger {

    var predictions = PredictionMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Prediction started")
    }
}