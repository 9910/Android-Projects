package com.example.makro123.numbizzapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor? = null
    var mSensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up sensors
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    var xold = 0.0
    var yold = 0.0
    var zold = 0.0
    var threadShould = 3000.0
    var oldTime: Long = 0
    override fun onSensorChanged(event: SensorEvent?) {
        // Get X, Y, Z values
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val currentTime = System.currentTimeMillis()

        if (currentTime - oldTime > 100) {

            val timeDiff = currentTime - oldTime
            oldTime = currentTime
            val speed = Math.abs(x + y + z - xold - yold - zold) / timeDiff * 10000

            if (speed > threadShould) { // Vibrate and stuff
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(500)
                Toast.makeText(applicationContext, "Shock", Toast.LENGTH_LONG).show()
            }
        }

    }

}
