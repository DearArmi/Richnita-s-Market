package com.example.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.earthquakemonitor.databinding.ActivityEarthquakeDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class EarthquakeDetail : AppCompatActivity() {

    companion object {
        const val EARTHQUAKE_KEY = "earthquake"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEarthquakeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras!!
        val earthqueake = extras.getParcelable<Earthquake>(EARTHQUAKE_KEY)!!


        binding.mag.text = earthqueake.magnitude.toString()
        binding.longitude2.text = earthqueake.longitude.toString()
        binding.latitude2.text = earthqueake.latitude.toString()
        binding.city.text = earthqueake.place

        binding.time.text = date(earthqueake.time)

    }

    private fun date(time:Long):String {

        val simpleDateFormat = SimpleDateFormat("dd/MMM/yyyy H:mm:ss", Locale.getDefault())
        val date = Date(time)
        val formattedString = simpleDateFormat.format(date)

        return  formattedString
    }


}