package com.example.makro123.alarmapp

import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import kotlinx.android.synthetic.main.pop_time.view.*

class PopTime: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val mView = inflater!!.inflate(R.layout.pop_time, container, false)
        val buDone = mView.buDone as Button
        val tp1 = mView.tp1 as TimePicker

        buDone.setOnClickListener {
            // Show "PopTime" Fragment Dialog
            val ma = activity as MainActivity
            if (Build.VERSION.SDK_INT >= 23)
                ma.setTime(tp1.hour, tp1.minute)
            else
                ma.setTime(tp1.currentHour, tp1.currentMinute)

            this.dismiss()
        }

        return  mView
    }

}