package org.wit.fatpredictor.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.fatpredictor.models.PredictModel
import org.wit.fatpredictor.models.PredictionJSONStore
import org.wit.fatpredictor.models.PredictionMemStore
import org.wit.fatpredictor.models.PredictionStore

class MainApp : Application(), AnkoLogger {

    lateinit var predictions : PredictionStore

    override fun onCreate() {
        super.onCreate()
        predictions = PredictionJSONStore(applicationContext)
        info("Prediction started")
    }
}