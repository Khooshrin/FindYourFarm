package com.example.satelliteimageprocessing

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.satelliteimageprocessing.databinding.ActivityMapsBinding
import com.example.satelliteimageprocessing.ml.FinalDataset
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import kotlin.concurrent.schedule


/*class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var imageSize = 224
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        // Add a marker in Sydney and move the camera
        val latitude=intent.getStringExtra("Lat").toString().toDouble()
        val longitude=intent.getStringExtra("Long").toString().toDouble()
        val testfarm = LatLng(latitude, longitude)
        val zoomlevel=16.0

        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testfarm, zoomlevel.toFloat()))

        var past = this
       var ximage: Bitmap?
       var image: Bitmap?
       ximage=null
        image=null
        var classes: Array<String>
   mMap.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
       // Make a snapshot when map's done loading
       mMap.snapshot(GoogleMap.SnapshotReadyCallback { bitmap ->
//           image = null
           /// make the bitmap null so everytime it //will fetch the new bitmap
           ximage = bitmap
       })
   })

        var check = "HI"
        Timer().schedule(2000) {

//
            val model = FinalDataset.newInstance(past)

            // Creates inputs for reference.

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//                    TensorBuffer.createDynamic(DataType.FLOAT32)
                val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
//

////    get 1D array of 224 * 224 pixels in image
////
////    get 1D array of 224 * 224 pixels in image
            val intValues = IntArray(imageSize * imageSize)

            if (ximage == null) check = "dead"
//            image = ximage?.let {
//                Bitmap.createScaledBitmap(
//                    it, 1080, 1080, false
//                )
//            }
            image = ximage?.let { Bitmap.createBitmap(it, 428, 890, 224, 224) }
//        check = image?.getHeight().toString()
            image!!.getPixels(
                intValues,
                0,
                image!!.getWidth(),
                0,
                0,
                image!!.getWidth(),
                image!!.getHeight()
            )

//
//   // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//
//   // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
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
   val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
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
    classes = arrayOf("Farmland Detected", "No Farmland Detected")
    check = classes[maxPos]


            // Releases model resources if no longer used.
            model.close()
    }
            Handler().postDelayed( {

                val stream = ByteArrayOutputStream()
                image?.compress(Bitmap.CompressFormat.JPEG, 98, stream)
                val byteArray = stream.toByteArray()

                val intent= Intent(past,FarmVerdict::class.java)
                if(image != null){
                    intent.putExtra("Verdict", check)
                }
                else {
                    intent.putExtra("Verdict", "Farm found")
                }
                intent.putExtra("Image", byteArray)

                intent.putExtra("Lat", latitude.toString())
                intent.putExtra("Long", longitude.toString())

                startActivity(intent)
                finish()
            } ,10000)
    }
}*/


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var imageSize = 224
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        // Add a marker in Sydney and move the camera
        val latitude=intent.getStringExtra("Lat").toString().toDouble()
        val longitude=intent.getStringExtra("Long").toString().toDouble()
        val testfarm = LatLng(latitude, longitude)
        val zoomlevel=16.0
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(testfarm, zoomlevel.toFloat()))

        var past = this
        var ximage: Bitmap?
        var image: Bitmap?
        ximage=null
        image=null
        var classes: Array<String>
        mMap.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
            // Make a snapshot when map's done loading
            mMap.snapshot(GoogleMap.SnapshotReadyCallback { bitmap ->
//           image = null
                /// make the bitmap null so everytime it //will fetch the new bitmap
                ximage = bitmap
            })
        })

        var check = "HI"
        Timer().schedule(5000) {

//
            val model = FinalDataset.newInstance(past)

            // Creates inputs for reference.

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//                    TensorBuffer.createDynamic(DataType.FLOAT32)
            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
//

////    get 1D array of 224 * 224 pixels in image
////
////    get 1D array of 224 * 224 pixels in image
            val intValues = IntArray(imageSize * imageSize)

            if (ximage == null) check = "dead"
//            image = ximage?.let {
//                Bitmap.createScaledBitmap(
//                    it, 1080, 1080, false
//                )
//            }
            image = ximage?.let { Bitmap.createBitmap(it, 428, 890, 224, 224) }
//        check = image?.getHeight().toString()
            image!!.getPixels(
                intValues,
                0,
                image!!.getWidth(),
                0,
                0,
                image!!.getWidth(),
                image!!.getHeight()
            )

//
//   // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//
//   // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
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
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
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
            classes = arrayOf("Farmland Detected", "No Farmland Detected")
            check = classes[maxPos]


            // Releases model resources if no longer used.
            model.close()
        }
        Handler().postDelayed( {

            val stream = ByteArrayOutputStream()
            image?.compress(Bitmap.CompressFormat.JPEG, 98, stream)
            val byteArray = stream.toByteArray()

            val intent= Intent(past,FarmVerdict::class.java)
            if(image != null){
                intent.putExtra("Verdict", check)

            }
            else {
                intent.putExtra("Verdict", "Farm found")
            }
            intent.putExtra("Lat", latitude.toString())
            intent.putExtra("Long", longitude.toString())
            intent.putExtra("Image", byteArray)
            startActivity(intent)
            finish()
        } ,10000)


//


    }
}