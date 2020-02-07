package org.wit.fatpredictor.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.fatpredictor.models.PredictModel

@Database(entities = arrayOf(PredictModel::class), version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {

    abstract fun predictionDao():PredictionDao
}