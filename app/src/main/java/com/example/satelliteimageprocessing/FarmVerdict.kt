package com.example.satelliteimageprocessing

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FarmVerdict : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_verdict)
        val verdictext=findViewById<TextView>(R.id.verdict)
        val imageView = findViewById<ImageView>(R.id.imageView4);
        val nextButton = findViewById<Button>(R.id.next)
        val bmp: Bitmap
        val latitude=intent.getStringExtra("Lat").toString()
        val longitude=intent.getStringExtra("Long").toString()

        var output = intent.getStringExtra("Verdict")
        if(output == "No Farmland Detected"){

            nextButton.visibility = View.INVISIBLE
        }
        val byteArray = intent.getByteArrayExtra("Image")
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        imageView.setImageBitmap(bmp)
        verdictext.setText(output)

        nextButton.setOnClickListener{
            val intent = Intent(this, FarmFeatures::class.java)
            intent.putExtra("Lat", latitude)
            intent.putExtra("Long", longitude)
            intent.putExtra("Image", byteArray)
            startActivity(intent)
            finish()
        }
    }
}