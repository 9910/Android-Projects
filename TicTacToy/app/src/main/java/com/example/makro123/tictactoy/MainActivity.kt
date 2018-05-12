package com.example.makro123.tictactoy

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun buClick(view: View) {

        val buSelected:Button = view as Button
        var cellID = 0

        when(buSelected.id) {
            R.id.bu1 -> cellID = 1
            R.id.bu2 -> cellID = 2
            R.id.bu3 -> cellID = 3
            R.id.bu4 -> cellID = 4
            R.id.bu5 -> cellID = 5
            R.id.bu6 -> cellID = 6
            R.id.bu7 -> cellID = 7
            R.id.bu8 -> cellID = 8
            R.id.bu9 -> cellID = 9
        }

        //Toast.makeText(this, "ID: " + cellID, Toast.LENGTH_SHORT).show()

        playGame(cellID, buSelected)
    }

    var Player1 = ArrayList<Int>()
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1

    fun playGame(cellID: Int, buSelected: Button) {

        if(ActivePlayer == 1) {
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.GREEN)
            Player1.add(cellID)
            ActivePlayer = 2
            autoPlay()
        } else {
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.BLUE)
            Player2.add(cellID)
            ActivePlayer = 1
        }

        buSelected.isEnabled = false
        checkWiner()

    }

    fun checkWiner() {

        var winer = -1

        // Row 1
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3))
            winer = 1
        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3))
            winer = 2

        // Row 2
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6))
            winer = 1
        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6))
            winer = 2

        // Row 3
        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9))
            winer = 1
        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9))
            winer = 2

        // Column 1
        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7))
            winer = 1
        if(Player2.contains(6) && Player2.contains(7) && Player2.contains(7))
            winer = 2

        // Column 2
        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8))
            winer = 1
        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8))
            winer = 2

        // Column 3
        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9))
            winer = 1
        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9))
            winer = 2

        // Diagonal 1
        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7))
            winer = 1
        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7))
            winer = 2

        // Diagonal 2
        if(Player1.contains(3) && Player1.contains(5) && Player1.contains(7))
            winer = 1
        if(Player2.contains(3) && Player2.contains(5) && Player2.contains(7))
            winer = 2

        // Check Winner
        if(winer != -1) {
            if(winer == 1)
                Toast.makeText(this, "Player 1 Wins The Game", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Player 2 Wins The Game", Toast.LENGTH_SHORT).show()
        }

    }

    fun autoPlay() {

        var emptyCells = ArrayList<Int>()

        for(cellID in 1..9)
            if(!(Player1.contains(cellID) || Player2.contains(cellID)))
                emptyCells.add(cellID)

        var r = Random()
        var randIndex = r.nextInt(emptyCells.size - 0) + 0
        val cellID = emptyCells[randIndex]

        var buSelected: Button?
        when(cellID) {
            1 -> buSelected = bu1
            2 -> buSelected = bu2
            3 -> buSelected = bu3
            4 -> buSelected = bu4
            5 -> buSelected = bu5
            6 -> buSelected = bu6
            7 -> buSelected = bu7
            8 -> buSelected = bu8
            9 -> buSelected = bu9
            else -> {
                buSelected = bu1
            }
        }

        playGame(cellID, buSelected)

    }

}
