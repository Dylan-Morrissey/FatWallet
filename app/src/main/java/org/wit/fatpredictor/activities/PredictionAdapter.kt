package org.wit.fatpredictor.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_prediction.view.*
import org.wit.fatpredictor.R
import org.wit.fatpredictor.helpers.readImageFromPath
import org.wit.fatpredictor.models.PredictModel

interface PredictListener {
    fun onPredictionClick(prediction: PredictModel)
}

class PredictionAdapter constructor(private var predictions: List<PredictModel>, private val listener: PredictListener) :
    RecyclerView.Adapter<PredictionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_prediction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val prediction = predictions[holder.adapterPosition]
        holder.bind(prediction, listener)
    }

    override fun getItemCount(): Int = predictions.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(prediction: PredictModel, listener: PredictListener) {
            itemView.predictWeight.text = prediction.weight + " lbs"
            itemView.predictHeight.text = prediction.height + " cm"
            itemView.bodyfatPer.text = prediction.bodyfat + "%"
            Glide.with(itemView.context).load(prediction.image).into(itemView.imageIcon);
            //itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, prediction.image))
            itemView.setOnClickListener { listener.onPredictionClick(prediction) }
        }
    }
}
