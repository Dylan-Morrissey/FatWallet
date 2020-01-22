package org.wit.fatpredictor.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_prediction_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.fatpredictor.R
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel


class PredictionListActivity : AppCompatActivity(), PredictListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadPredictons()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPredictionClick(prediction: PredictModel) {
        startActivityForResult(intentFor<PredictActivity>().putExtra("predict_edit", prediction), 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.item_add -> startActivityForResult<PredictActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPredictons()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadPredictons() {
        showPredictions(app.predictions.findAll())
    }

    fun showPredictions(predictons: List<PredictModel>) {
        recyclerView.adapter = PredictionAdapter(predictons, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}



