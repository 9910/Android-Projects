package com.example.makro123.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action.equals("com.tester.alarmmanager")) {

            val b = intent.extras
            val mNotification = Notification()

            mNotification.Notify(context!!, b.getString("message"), 10)
//            Toast.makeText(context, b.getString("message"), Toast.LENGTH_LONG).show()

        } else if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            // Read data after boot
            val saveData = SaveData(context!!)
            saveData.setAlarm()

        }
    }
}