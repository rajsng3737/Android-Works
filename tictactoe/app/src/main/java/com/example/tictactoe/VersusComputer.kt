package com.example.thegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class VersusComputer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus_computer)
    }

    var count = 0
    var someoneWon = false
    var player = 1
    var playerClicks = ArrayList<Int>()
    var computerClicks = ArrayList<Int>()
    var playerRecord = 0
    lateinit var compButton: Button
    var buttonId = 0
    var computerRecord = 0
    var scoreCard: TextView? = null
    var choices = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
    fun buttonClicked(view: View) {
        var whichButton = view as Button
        when (whichButton.id) {
            R.id.button1 -> buttonId = 1
            R.id.button2 -> buttonId = 2
            R.id.button3 -> buttonId = 3
            R.id.button4 -> buttonId = 4
            R.id.button5 -> buttonId = 5
            R.id.button6 -> buttonId = 6
            R.id.button7 -> buttonId = 7
            R.id.button8 -> buttonId = 8
            R.id.button9 -> buttonId = 9
        }
        count += 1
        playerClicks.add(buttonId)
        drawOnButton(buttonId, whichButton)
        choices.remove(buttonId)
        if (!someoneWon)
            computerTurn()
        someoneWon = false
    }

    fun computerTurn() {
        buttonId = choices[(0..choices.size - 1).random()]
        compButton = when (buttonId) {
            1 -> findViewById(R.id.button1)
            2 -> findViewById(R.id.button2)
            3 -> findViewById(R.id.button3)
            4 -> findViewById(R.id.button4)
            5 -> findViewById(R.id.button5)
            6 -> findViewById(R.id.button6)
            7 -> findViewById(R.id.button7)
            8 -> findViewById(R.id.button8)
            9 -> findViewById(R.id.button9)

            else -> findViewById(R.id.button1)
        }
        count += 1
        computerClicks.add(buttonId)
        drawOnButton(buttonId, compButton)
        choices.remove(buttonId)
    }

    fun drawOnButton(buttonId: Int, whichButton: Button) {
        if (player == 1) {
            whichButton.text = "X"
            whichButton.setBackgroundColor(resources.getColor(R.color.player1Color))
            player = 0
            whichButton.isEnabled = false
            if (count >= 4 && count <= 9)
                checkWin(1)
        } else {
            whichButton.text = "O"
            whichButton.setBackgroundColor(resources.getColor(R.color.player2Color))
            player = 1
            whichButton.isEnabled = false
            if (count >= 5)
                checkWin(0)
        }
    }

    fun checkWin(playerOrComp: Int) {
        var winner = 0
        if (playerOrComp == 1) {
            if (playerClicks.contains(1) && playerClicks.contains(2) && playerClicks.contains(3))
                winner = 1
            else if (playerClicks.contains(4) && playerClicks.contains(5) && playerClicks.contains(6))
                winner = 1
            else if (playerClicks.contains(7) && playerClicks.contains(8) && playerClicks.contains(9))
                winner = 1
            else if (playerClicks.contains(1) && playerClicks.contains(4) && playerClicks.contains(7))
                winner = 1
            else if (playerClicks.contains(2) && playerClicks.contains(5) && playerClicks.contains(8))
                winner = 1
            else if (playerClicks.contains(3) && playerClicks.contains(6) && playerClicks.contains(9))
                winner = 1
            else if (playerClicks.contains(1) && playerClicks.contains(5) && playerClicks.contains(9))
                winner = 1
            else if (playerClicks.contains(3) && playerClicks.contains(5) && playerClicks.contains(7))
                winner = 1
            else if (count == 9) {
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                restart()
            }
        } else {
            if (computerClicks.contains(1) && computerClicks.contains(2) && computerClicks.contains(3))
                winner = 2
            else if (computerClicks.contains(4) && computerClicks.contains(5) && computerClicks.contains(6))
                winner = 2
            else if (computerClicks.contains(7) && computerClicks.contains(8) && computerClicks.contains(9))
                winner = 2
            else if (computerClicks.contains(1) && computerClicks.contains(4) && computerClicks.contains(7))
                winner = 2
            else if (computerClicks.contains(2) && computerClicks.contains(5) && computerClicks.contains(8))
                winner = 2
            else if (computerClicks.contains(3) && computerClicks.contains(6) && computerClicks.contains(9))
                winner = 2
            else if (computerClicks.contains(1) && computerClicks.contains(5) && computerClicks.contains(9))
                winner = 2
            else if (computerClicks.contains(3) && computerClicks.contains(5) && computerClicks.contains(7))
                winner = 2
        }
        if (winner == 1) {
            Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show()
            playerRecord += 1
            restart()
        } else if (winner == 2) {
            Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show()
            computerRecord += 1
            restart()
        }
    }

    fun restart() {
        var clearAllButton: Button?
        choices.clear()
        playerClicks.clear()
        computerClicks.clear()
        for (i in 1..9) {
            choices.add(i)
            clearAllButton = when (i) {
                1 -> findViewById(R.id.button1)
                2 -> findViewById(R.id.button2)
                3 -> findViewById(R.id.button3)
                4 -> findViewById(R.id.button4)
                5 -> findViewById(R.id.button5)
                6 -> findViewById(R.id.button6)
                7 -> findViewById(R.id.button7)
                8 -> findViewById(R.id.button8)
                9 -> findViewById(R.id.button9)
                else -> null
            }
            player = 1
            clearAllButton!!.setBackgroundColor(resources.getColor(R.color.teal_200))
            clearAllButton.text = ""
            clearAllButton.isEnabled = true
            someoneWon = true
            count = 0
        }
        scoreCard = findViewById(R.id.win_records)
        scoreCard!!.text = "You   : $playerRecord \n Computer : $computerRecord"
    }
}