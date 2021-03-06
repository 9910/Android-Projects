package com.example.makro123.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buNumbersEvent(view: View) {

        if(isNewOp)
            editShowNumber.setText("")

        isNewOp = false
        val buSelect = view as Button
        var buClickValue: String = editShowNumber.text.toString()

        when(buSelect.id) {
            bu0.id -> buClickValue += "0"
            bu1.id -> buClickValue += "1"
            bu2.id -> buClickValue += "2"
            bu3.id -> buClickValue += "3"
            bu4.id -> buClickValue += "4"
            bu5.id -> buClickValue += "5"
            bu6.id -> buClickValue += "6"
            bu7.id -> buClickValue += "7"
            bu8.id -> buClickValue += "8"
            bu9.id -> buClickValue += "9"
            buDot.id -> buClickValue += "."
            buPlusMinus.id -> {
                buClickValue = "-" + buClickValue
            }
        }

        editShowNumber.setText(buClickValue)

    }

    var op = "*"
    var oldNumber = ""
    var isNewOp = true

    fun buOpEvent(view: View) {

        val buSelect = view as Button
        var buClickValue: String = editShowNumber.text.toString()

        when(buSelect.id) {
            buMul.id -> {
                op = "*"
            }
            buDiv.id -> {
                op = "/"
            }
            buSub.id -> {

                op = "-"
            }
            buSum.id -> {
                op = "+"
            }
        }

        oldNumber = editShowNumber.text.toString()
        isNewOp = true

    }

    fun buEqualEvent(view: View) {

        val newNumber = editShowNumber.text.toString()
        var finalNumber: Double? = null

        when(op) {
            "*" -> {
                finalNumber = oldNumber.toDouble() * newNumber.toDouble()
            }
            "/" -> {
                finalNumber = oldNumber.toDouble() / newNumber.toDouble()
            }
            "+" -> {
                finalNumber = oldNumber.toDouble() + newNumber.toDouble()
            }
            "-" -> {
                finalNumber = oldNumber.toDouble() - newNumber.toDouble()
            }
        }

        editShowNumber.setText(finalNumber.toString())
        isNewOp = true

    }

    fun onPercent(view: View) {
        val number: Double = editShowNumber.text.toString().toDouble() / 100
        editShowNumber.setText(number.toString())
    }

    fun buClean(view: View) {
        editShowNumber.setText("0")
        isNewOp = true
    }

}
