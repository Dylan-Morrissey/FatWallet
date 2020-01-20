package org.wit.fatpredictor.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_prediction.view.*
import org.wit.fatpredictor.R
import org.wit.fatpredictor.helpers.readImage
import org.wit.fatpredictor.helpers.readImageFromPath
import org.wit.fatpredictor.models.PredictModel

interface PredictListener {
    fun onPlacemarkClick(prediction: PredictModel)
}

class PredictionAdapter constructor(private var placemarks: List<PredictModel>) :
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
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(prediction: PredictModel) {
            itemView.placemarkTitle.text = prediction.weight
            itemView.description.text = prediction.height
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, prediction.image))
        }
    }
}
