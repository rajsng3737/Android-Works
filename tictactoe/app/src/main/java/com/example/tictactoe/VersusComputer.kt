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
    private var count = 0
    private var someoneWon = false
    private var player = 1
    private var playerClicks = ArrayList<Int>()
    private var computerClicks = ArrayList<Int>()
    private var playerRecord = 0
    private lateinit var compButton: Button
    private var buttonId = 0
    private var computerRecord = 0
    private var scoreCard: TextView? = null
    private var choices = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
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

    private fun computerTurn() {
        buttonId = choices.random()
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

    private fun drawOnButton(buttonId: Int, whichButton: Button) {
        if (player == 1) {
            whichButton.text = "X"
            whichButton.setBackgroundColor(resources.getColor(R.color.player1Color))
            player = 0
            whichButton.isEnabled = false
            if (count in 5..9)
                checkWin(1)
        } else {
            whichButton.text = "O"
            whichButton.setBackgroundColor(resources.getColor(R.color.player2Color))
            player = 1
            whichButton.isEnabled = false
            if (count in 5..9)
                checkWin(2)
        }
    }

    private fun checkWin(playerOrComp: Int) {
        var winner = 0
        if (playerOrComp == 1) {
            val result = calculateWin(playerClicks)
            if(result == 1)
                winner = playerOrComp;
            else if(result == 2 || result == 0)
                return
        } else {
            val result = calculateWin(computerClicks)
            if(result == 1)
                winner = playerOrComp;
            else if(result == 2 || result == 0)
                    return
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

    private fun calculateWin( clicks : ArrayList<Int>) : Int {
        if (clicks.contains(1) && clicks.contains(2) && clicks.contains(3))
            return 1
        else if (clicks.contains(4) && clicks.contains(5) && clicks.contains(6))
            return 1
        else if (clicks.contains(7) && clicks.contains(8) && clicks.contains(9))
            return 1
        else if (clicks.contains(1) && clicks.contains(4) && clicks.contains(7))
            return 1
        else if (clicks.contains(2) && clicks.contains(5) && clicks.contains(8))
            return 1
        else if (clicks.contains(3) && clicks.contains(6) && clicks.contains(9))
            return 1
        else if (clicks.contains(1) && clicks.contains(5) && clicks.contains(9))
            return 1
        else if (clicks.contains(3) && clicks.contains(5) && clicks.contains(7))
            return 1
        else if (count == 9) {
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
            restart()
            return 2
        }
        return 0
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

    fun vsComputer(view: View) {}
}