package com.example.satelliteimageprocessing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.time.Duration

class GetFarmDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_farm_details)
        val submitButton=findViewById<Button>(R.id.SubmitDetails)
        val switchHindi=findViewById<Switch>(R.id.hindi)
        var wantHindi=switchHindi.isChecked

        switchHindi.setOnCheckedChangeListener { _, isChecked ->
            // do whatever you need to do when the switch is toggled here
            wantHindi = isChecked
        }




        submitButton.setOnClickListener{
            val latitude=findViewById<EditText>(R.id.LatCoordinates).text.toString()
            val longitude=findViewById<EditText>(R.id.LongCoordinates).text.toString()
//            val latitude="26.5775"
//            val longitude ="93.1711"

            val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("Lat", latitude)
                intent.putExtra("Long", longitude)
                startActivity(intent)
            finish()

            }
    }


}