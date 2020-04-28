package org.wit.fatpredictor.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_prediction_list.*
import org.jetbrains.anko.*
import org.wit.fatpredictor.R
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel


class PredictionListActivity : AppCompatActivity(), PredictListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction_list)
        app = application as MainApp

        toolbar.title = "Predictions"
        setSupportActionBar(toolbar)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${user.email} Predictions"
        }

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
            R.id.item_logout -> startActivity(Intent(baseContext, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPredictons()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadPredictons() {
        doAsync {
            val predictions = app.predictions.findAll()
            uiThread {
                showPredictions(predictions)
            }
        }
    }

    fun showPredictions(predictons: List<PredictModel>) {
        recyclerView.adapter = PredictionAdapter(predictons, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}



