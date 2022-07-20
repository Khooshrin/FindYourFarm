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
        val titleText=findViewById<TextView>(R.id.TitleText)
        val latitudeText=findViewById<TextView>(R.id.Latitude)
        val longitudeText=findViewById<TextView>(R.id.Longitude)
        switchHindi.setOnCheckedChangeListener { _, isChecked ->
            // do whatever you need to do when the switch is toggled here
            wantHindi = isChecked

            if(wantHindi) titleText.setText("खेत का विवरण")
            else titleText.setText("Farm Details")

            if(wantHindi) latitudeText.setText("खेत अक्षांश दर्ज करें")
            else latitudeText.setText("Enter Farm Latitude")

            if(wantHindi) longitudeText.setText("खेत देशांतर दर्ज करें")
            else longitudeText.setText("Enter Farm Longitude")

            if(wantHindi) switchHindi.setText("अंग्रेज़ी पर स्विच करें")
            else switchHindi.setText("Switch to Hindi")

            if(wantHindi) submitButton.setText("आगे बढ़े")
            else submitButton.setText("Submit")
        }




        submitButton.setOnClickListener{
            val latitude=findViewById<EditText>(R.id.LatCoordinates).text.toString()
            val longitude=findViewById<EditText>(R.id.LongCoordinates).text.toString()
//            val latitude="26.5775"
//          val longitude ="93.1711"
//           val latitude = "74.8643260"
//            val longitude = "27.1897911"

            val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("Lat", latitude)
                intent.putExtra("Long", longitude)
                intent.putExtra("WantHindi", wantHindi)
                startActivity(intent)
            finish()

            }
    }


}

