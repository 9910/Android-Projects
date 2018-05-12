package com.example.makro123.lightsensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set sensors
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    var isRunning: Boolean = false
    override fun onSensorChanged(event: SensorEvent?) {

        if (event!!.values[0] > 40 && !isRunning) {
            // Play music
            isRunning = true
            try {
                val mMediaPlayer: MediaPlayer? = MediaPlayer()
                mMediaPlayer!!.setDataSource("https://server6:mp3quran.net/thubti/001.mp3")
                mMediaPlayer.prepare()
                mMediaPlayer.start()

            } catch (ex: Exception) {}
        }

    }
}
