package org.wit.fatpredictor.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_prediction_list.*
import kotlinx.android.synthetic.main.card_prediction.view.*
import org.jetbrains.anko.startActivityForResult
import org.wit.fatpredictor.R
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel

class PredictionListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PredictionAdapter(app.predictions)
    }
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
            }
        }
    }

