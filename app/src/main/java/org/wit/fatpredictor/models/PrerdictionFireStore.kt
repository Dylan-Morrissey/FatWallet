package org.wit.fatpredictor.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger


class PredictionFireStore(val context: Context) : PredictionStore, AnkoLogger {

    val predictions = ArrayList<PredictModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<PredictModel> {
        return predictions
    }

    override fun findById(id: Long): PredictModel? {
        val foundPrediction: PredictModel? = predictions.find { p -> p.id == id }
        return foundPrediction
    }

    override fun create(prediction: PredictModel) {
        val key = db.child("users").child(userId).child("predictions").push().key
        key?.let {
            prediction.fbId = key
            predictions.add(prediction)
            db.child("users").child(userId).child("predictions").child(key).setValue(prediction)
        }
    }

    override fun update(prediction: PredictModel) {
        var foundPrediction: PredictModel? = predictions.find { p -> p.fbId == prediction.fbId }
        if (foundPrediction != null) {
            foundPrediction.weight = prediction.weight
            foundPrediction.height = prediction.height
            foundPrediction.image = prediction.image
        }
        db.child("users").child(userId).child("predictions").child(prediction.fbId).setValue(prediction)

    }


    override fun delete(prediction: PredictModel) {
        db.child("users").child(userId).child("predictions").child(prediction.fbId).removeValue()
        predictions.remove(prediction)
    }

    override fun clear() {
        predictions.clear()
    }

    fun fetchPredictions(predictionsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(predictions) { it.getValue<PredictModel>(PredictModel::class.java) }
                predictionsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        predictions.clear()
        db.child("users").child(userId).child("predictions").addListenerForSingleValueEvent(valueEventListener)
    }
}