/*

package org.wit.fatpredictor.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.fatpredictor.helpers.exists
import org.wit.fatpredictor.helpers.read
import org.wit.fatpredictor.helpers.write
import java.util.*

val JSON_FILE = "prerdiction.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PredictModel>>() {}.type



fun generateRandomId(): Long {
  return Random().nextLong()
}
class PredictionJSONStore : PredictionStore, AnkoLogger {

  val context: Context
  var predictions = mutableListOf<PredictModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<PredictModel> {
    return predictions
  }

  override fun create(prediction: PredictModel) {
    prediction.id = generateRandomId()
    predictions.add(prediction)
    serialize()
  }

  override fun update(prediction: PredictModel) {
    val predictionsList = findAll() as ArrayList<PredictModel>
    var foundPrediction: PredictModel? = predictionsList.find { p -> p.id == prediction.id }
    if (foundPrediction != null) {
      foundPrediction.weight = prediction.weight
      foundPrediction.height = prediction.height
      foundPrediction.image = prediction.image
    }
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(predictions, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    predictions = Gson().fromJson(jsonString, listType)
  }
}
 */