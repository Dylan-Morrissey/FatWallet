package org.wit.fatpredictor.models

interface PredictionStore {
    fun findAll(): List<PredictModel>
    fun create(prediction: PredictModel)
    fun update(prediction: PredictModel)
    fun delete(prediction: PredictModel)
    fun findById (id:Long): PredictModel?
    fun clear()
}