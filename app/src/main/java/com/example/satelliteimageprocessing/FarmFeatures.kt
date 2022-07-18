package com.example.satelliteimageprocessing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.satelliteimageprocessing.ml.CroppingPatternDataset
import com.example.satelliteimageprocessing.ml.IrrigationDataset
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FarmFeatures : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_features)
        val irrigation = findViewById<TextView>(R.id.Irrigation)
        val cropping=findViewById<TextView>(R.id.CroppingPattern)
        val latitude=intent.getStringExtra("Lat").toString()
        val longitude=intent.getStringExtra("Long").toString()
        val bmp: Bitmap
        val byteArray = intent.getByteArrayExtra("Image")
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val imageSize = 224

        var model = IrrigationDataset.newInstance(this)

        // Creates inputs for reference.

        // Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//                    TensorBuffer.createDynamic(DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(imageSize * imageSize)

        val image: Bitmap = bmp.let { Bitmap.createBitmap(it, 0, 0, 224, 224) }
//        check = image?.getHeight().toString()
        image.getPixels(
            intValues,
            0,
            image.width,
            0,
            0,
            image.width,
            image.height
        )


        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }
        Log.d("shape", byteBuffer.toString())
        Log.d("shape", inputFeature0.buffer.toString())

        inputFeature0.loadBuffer(byteBuffer)
//
////    Runs model inference and gets result.
////
////    Runs model inference and gets result.

        val outputs = model.process(inputFeature0)
        val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
//
        val confidences = outputFeature0.floatArray
//   // find the index of the class with the biggest confidence.
//   // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes: Array<String> = arrayOf("Basin Irrigation", "Drip Irrigation","Furrow Irrigation","Sprinkler Irrigation","Surface Irrigation")
        irrigation.text = classes[maxPos]
        model.close()


        var model1 = CroppingPatternDataset.newInstance(this)

        // Creates inputs for reference.

        // Creates inputs for reference.
        val inputFeature01 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//                    TensorBuffer.createDynamic(DataType.FLOAT32)
        val byteBuffer1: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues1 = IntArray(imageSize * imageSize)

        val image1: Bitmap = bmp.let { Bitmap.createBitmap(it, 0, 0, 224, 224) }
//        check = image?.getHeight().toString()
        image1.getPixels(
            intValues1,
            0,
            image1.width,
            0,
            0,
            image1.width,
            image1.height
        )


        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel1 = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel1++] // RGB
                byteBuffer1.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer1.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer1.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }
        Log.d("shape", byteBuffer1.toString())
        Log.d("shape", inputFeature01.buffer.toString())

        inputFeature01.loadBuffer(byteBuffer1)
//
////    Runs model inference and gets result.
////
////    Runs model inference and gets result.

        val outputs1 = model1.process(inputFeature0)
        val outputFeature01: TensorBuffer = outputs1.outputFeature0AsTensorBuffer
//
        val confidences1 = outputFeature01.floatArray
//   // find the index of the class with the biggest confidence.
//   // find the index of the class with the biggest confidence.
        var maxPos1 = 0
        var maxConfidence1 = 0f
        for (i in confidences1.indices) {
            if (confidences1[i] > maxConfidence1) {
                maxConfidence1 = confidences1[i]
                maxPos1 = i
            }
        }
        val classes1: Array<String> = arrayOf("Intercropping Pattern", "Monocropping Pattern","Multicropping Pattern")
        cropping.text = classes1[maxPos1]
        model1.close()
    }
}