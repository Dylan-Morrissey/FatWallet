package org.wit.fatpredictor.room

import androidx.room.*
import org.wit.fatpredictor.models.PredictModel

@Dao
interface PredictionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(prediction: PredictModel)

    @Query("SELECT * FROM PredictModel")
    fun findAll(): List<PredictModel>

    @Query("select * from PredictModel where id = :id")
    fun findById(id: Long): PredictModel

    @Update
    fun update(prediction: PredictModel)

    @Delete
    fun deletePrediction(prediction: PredictModel)
}