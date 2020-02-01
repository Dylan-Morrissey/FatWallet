package org.wit.fatpredictor.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_prediction.*
import org.jetbrains.anko.*
import org.wit.fatpredictor.R
import org.wit.fatpredictor.helpers.readImage
import org.wit.fatpredictor.helpers.readImageFromPath
import org.wit.fatpredictor.helpers.showImagePicker
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel

class PredictActivity : AppCompatActivity(), AnkoLogger {

    var predict = PredictModel()
    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)
        app = application as MainApp
        info ("Predict Activity Started...")

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(intent.hasExtra("predict_edit")) {
            edit = true
            predict = intent.extras?.getParcelable<PredictModel>("predict_edit")!!
            weight.setText(predict.weight)
            height.setText(predict.height)
            btnAdd.setText(R.string.save_prediction)
            imageView.setImageBitmap(readImageFromPath(this, predict.image))
        }

        btnAdd.setOnClickListener() {
            predict.weight = weight.text.toString()
            predict.height = height.text.toString()
            if (predict.weight.isEmpty()) {
                toast("Please Enter a Correct details")
            } else {
                doAsync {
                    if (edit) {
                        app.predictions.update(predict)
                    } else {
                        app.predictions.create(predict)
                    }
                    uiThread {
                        finish()
                    }
                }
            }
            info("add Button Pressed: $predict")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
        imageView.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_prediction, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    predict.image = data.getData().toString()
                    imageView.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }
}
