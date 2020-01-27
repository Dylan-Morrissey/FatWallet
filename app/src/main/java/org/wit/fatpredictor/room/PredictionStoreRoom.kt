package org.wit.fatpredictor.room

import android.content.Context
import androidx.room.Room
import org.wit.fatpredictor.models.PredictModel
import org.wit.fatpredictor.models.PredictionStore

class PredictionStoreRoom(val context: Context) : PredictionStore {

    var dao: PredictionDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.predictionDao()
    }

    override fun findAll(): List<PredictModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): PredictModel? {
        return dao.findById(id)
    }

    override fun create(placemark: PredictModel) {
        dao.create(placemark)
    }

    override fun update(placemark: PredictModel) {
        dao.update(placemark)
    }

    override fun delete(placemark: PredictModel) {
        dao.deletePrediction(placemark)
    }
}