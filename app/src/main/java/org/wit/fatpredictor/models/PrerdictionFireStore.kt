package org.wit.fatpredictor.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.fatpredictor.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File


class PredictionFireStore(val context: Context) : PredictionStore, AnkoLogger {

    val predictions = ArrayList<PredictModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

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
            updateImage(prediction)
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
        if((prediction.image.length) > 0 && (prediction.image[0] != 'h')) {
            updateImage(prediction)
        }

    }

    override fun delete(prediction: PredictModel) {
        db.child("users").child(userId).child("predictions").child(prediction.fbId).removeValue()
        predictions.remove(prediction)
    }

    override fun clear() {
        predictions.clear()
    }

    fun updateImage(prediction: PredictModel) {
        if (prediction.image != "") {
            val fileName = File(prediction.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, prediction.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        prediction.image = it.toString()
                        db.child("users").child(userId).child("predictions").child(prediction.fbId).setValue(prediction)
                    }
                }
            }
        }
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
        st = FirebaseStorage.getInstance().reference
        predictions.clear()
        db.child("users").child(userId).child("predictions").addListenerForSingleValueEvent(valueEventListener)
    }
}