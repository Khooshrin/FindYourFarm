package com.example.satelliteimageprocessing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class FarmDetails : AppCompatActivity() {
    fun run(url: String, client: OkHttpClient): String? {
        val request = Request.Builder()
            .url(url)
            .build()
        var s: String?
        s = ""
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) { s = (response.body()?.string()) }
        })
        println(s);
        return s
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.farm_details)
        val submitButton=findViewById<Button>(R.id.GetDetails)
        val client = OkHttpClient()
        var output: String?
        output = ""
        output = run("https://api.weatherbit.io/v2.0/forecast/agweather?lat=38.0&lon=-78.0&key=01c5d46b5c5d4156862c1d2f6b00caf0", client)
        submitButton.setOnClickListener{
//          
            val latitude=intent.getStringExtra("Lat").toString().toDouble()
            val longitude=intent.getStringExtra("Long").toString().toDouble()
            val verdictext=findViewById<TextView>(R.id.textView)
//            verdictext.setText(latitude.toString())
            if(output == "") verdictext.setText("LOl")
            else  verdictext.setText(output)
        }



}}