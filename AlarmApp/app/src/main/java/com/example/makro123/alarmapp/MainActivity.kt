package com.example.makro123.alarmapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val saveData = SaveData(applicationContext)
        tvShowTime.text = saveData.getHour().toString() + ":" + saveData.getMinute().toString()
    }

    fun buSetTime(view: View) {
        val popTime = PopTime()
        val fm = fragmentManager
        popTime.show(fm, "Select Time")
    }

    fun setTime(hours: Int, minute: Int) {
        tvShowTime.text = hours.toString() + ":" + minute.toString()

        val saveData = SaveData(applicationContext)
        saveData.saveData(hours, minute)
        saveData.setAlarm()
    }
}
