package com.example.satelliteimageprocessing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FarmVerdict : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm_verdict)
        val verdictext=findViewById<TextView>(R.id.verdict)
        val imageView = findViewById<ImageView>(R.id.imageView4);


//        val src = BitmapFactory.decodeStream(openFileInput("myImage"));
//        val bitmap = intent.getParcelableExtra("Image") as Bitmap?
//        imageView.setImageBitmap(intent.getParcelableExtra("Image"))
//        imageView.setImageBitmap(src)
//        val size = src.byteCount
//        verdictext.setText(size.toString())

        val bmp: Bitmap

        val byteArray = intent.getByteArrayExtra("Image")
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        imageView.setImageBitmap(bmp)
        verdictext.setText(intent.getStringExtra("Verdict"))
    }
}