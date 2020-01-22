package org.wit.fatpredictor.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

//Replaced by JSON store
class PredictionMemStore : PredictionStore, AnkoLogger {

    val predictions = ArrayList<PredictModel>()

    override fun findAll(): List<PredictModel> {
        return predictions
    }

    override fun create(prediction: PredictModel) {
        prediction.id = getId()
        predictions.add(prediction)
        logAll()
    }

    override fun update(prediction: PredictModel) {
        var foundPrediction: PredictModel? = predictions.find { p -> p.id == prediction.id }
        if (foundPrediction != null) {
            foundPrediction.weight = prediction.weight
            foundPrediction.height = prediction.height
            foundPrediction.image = prediction.image
            logAll()
        }
    }

    fun logAll() {
        predictions.forEach { info("${it}") }
    }
}