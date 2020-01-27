package org.wit.fatpredictor.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.fatpredictor.models.PredictionStore
import org.wit.fatpredictor.room.PredictionStoreRoom

class MainApp : Application(), AnkoLogger {

    lateinit var predictions : PredictionStore

    override fun onCreate() {
        super.onCreate()
        predictions = PredictionStoreRoom(applicationContext)
        info("Prediction started")
    }
}