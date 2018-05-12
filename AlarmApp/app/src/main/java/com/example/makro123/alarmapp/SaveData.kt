package com.example.makro123.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.util.*

class SaveData {

    var context: Context? = null
    var sharedPre: SharedPreferences? = null

    constructor(context: Context) {
        this.context = context
        this.sharedPre = context.getSharedPreferences("myref", Context.MODE_PRIVATE)
    }

    fun saveData(hour: Int, minute: Int) {
        val editor = sharedPre!!.edit()
        editor.putInt("hours", hour)
        editor.putInt("minute", minute)
        editor.apply()
    }

    fun getHour(): Int {
        return sharedPre!!.getInt("hours", 0)
    }

    fun getMinute(): Int {
        return sharedPre!!.getInt("minute", 0)
    }

    fun setAlarm() {

        val hour: Int = getHour()
        val minute: Int = getMinute()

        // Create calendar for the alarm time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Define AlarmManager
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create Intent for the action that the BroadcastReceiver will receive
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.putExtra("message", "alarm time")
        intent.action = "com.tester.alarmmanager"
        val pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set alarm time in the system
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }

}