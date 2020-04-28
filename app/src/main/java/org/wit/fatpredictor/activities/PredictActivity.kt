package org.wit.fatpredictor.activities

import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_prediction.*
import org.jetbrains.anko.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.wit.fatpredictor.R
import org.wit.fatpredictor.helpers.readImage
import org.wit.fatpredictor.helpers.showImagePicker
import org.wit.fatpredictor.main.MainApp
import org.wit.fatpredictor.models.PredictModel
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


@Suppress("DEPRECATION")
class PredictActivity : AppCompatActivity(), AnkoLogger {

    var predict = PredictModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)
        app = application as MainApp
        info("Predict Activity Started...")

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnDel.setVisibility(View.INVISIBLE)

        if (intent.hasExtra("predict_edit")) {
            edit = true
            predict = intent.extras?.getParcelable<PredictModel>("predict_edit")!!
            weight.setText(predict.weight)
            height.setText(predict.height)
            age.setText(predict.age.toString())
            btnAdd.setText(R.string.save_prediction)
            predictedBodyfatText.setText("Predicted Bodyfat range:  " + predict.bodyfat + "%")
            //imageView.setImageBitmap(readImageFromPath(this, predict.image))
            Glide.with(this).load(predict.image).into(imageView)
            btnDel.setVisibility(View.VISIBLE)

        }

        btnAdd.setOnClickListener() {
            if (weight.text.toString().toInt() < 5 || weight.text.toString().toInt() >1400) {
                toast("Please enter A valid weight.")
            }
            else if (height.text.toString().toInt() < 55 || height.text.toString().toInt() > 300) {
                toast("Please enter a valid Height")
            } else if (age.text.toString().toInt() < 15 || age.text.toString().toInt() > 150) {
                toast("Please enter a valid age.")
            }
            else {
                predict.weight = weight.text.toString()
                predict.height = height.text.toString()
                predict.age = age.text.toString().toInt()
                predict.bodyfat = predictonFromModel()
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
                info("add Button Pressed: $predict")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }


        }
        imageView.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        btnDel.setOnClickListener() {
            doDelete()
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

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val MODEL_ASSETS_PATH = "model.tflite"
        val assetFileDescriptor = assets.openFd(MODEL_ASSETS_PATH)
        val fileInputStream = FileInputStream(assetFileDescriptor.getFileDescriptor())
        val fileChannel = fileInputStream.getChannel()
        val startoffset = assetFileDescriptor.getStartOffset()
        val declaredLength = assetFileDescriptor.getDeclaredLength()
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength)
    }

    fun predictonFromModel(): String {
        val age = predict.age / 49
        val weight = predict.weight.toInt() / 307
        val height = predict.height.toInt() / 198.12
        val numericInput = listOf(age, weight, height)
        val interpreter = Interpreter(loadModelFile())
        val inputs = arrayOf(numericInput, predict.image)
        val output = 1
        //interpreter.run(inputs, output)
        return "10-12"
    }

    fun doDelete() {
        doAsync {
            app.predictions.delete(predict)
            finish()
        }
    }

    private fun loadImage(sensorOrientation: Int) {
        val width = imageView.getWidth()
        val height = imageView.getHeight()
        val imageProcessor = ImageProcessor.Builder()
            // Resize using Bilinear or Nearest neighbour
            .add(ResizeOp(200, 200, ResizeOp.ResizeMethod.BILINEAR)).build()
    }
}
