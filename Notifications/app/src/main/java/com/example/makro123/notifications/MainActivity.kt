package com.example.makro123.notifications

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alrubaye.notifymeapp.NotificationHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var notificationHelper: NotificationHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationHelper = NotificationHelper(this)
    }

    fun ch1Click(view: View) {
        notificationHelper!!.Notify(1, notificationHelper!!.getNotification1("First", etMessage.text.toString()))
    }

    fun ch2Click(view: View) {
        notificationHelper!!.Notify(2, notificationHelper!!.getNotification2("Second", etMessage.text.toString()))
    }

    fun ch3Click(view: View) {
        notificationHelper!!.Notify(3, notificationHelper!!.getNotification3("Third", etMessage.text.toString()))
    }

}
