package com.example.thegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class VersusPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus_player)
    }

    var player1Record = 0
    var player2Record = 0
    lateinit var scoreCard: TextView
    var player: Int = 1
    var player1Clicks = ArrayList<Int>()
    var player2Clicks = ArrayList<Int>()
    var count = 0
    fun buttonClicked(view: View) {
        val whichButton = view as Button
        var buttonId = 0
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
        drawOnButton(buttonId, whichButton)
    }

    fun drawOnButton(buttonId: Int, whichButton: Button) {
        if (player == 1) {
            whichButton.text = "X"
            whichButton.setBackgroundColor(resources.getColor(R.color.player1Color))
            player1Clicks.add(buttonId)
            whichButton.isEnabled = false
            player = 0
            if (count >= 5 && count <= 9)
                checkWin(1)
        } else {
            whichButton.text = "O"
            whichButton.setBackgroundColor(resources.getColor(R.color.player2Color))
            player2Clicks.add(buttonId)
            player = 1
            whichButton.isEnabled = false
            if (count >= 6 && count < 9)
                checkWin(2)
        }
    }

    fun checkWin(whichPlayer: Int) {
        var winner = 0
        if (whichPlayer == 1) {
            if (player1Clicks.contains(1) && player1Clicks.contains(2) && player1Clicks.contains(3))
                winner = 1
            else if (player1Clicks.contains(4) && player1Clicks.contains(5) && player1Clicks.contains(6))
                winner = 1
            else if (player1Clicks.contains(7) && player1Clicks.contains(8) && player1Clicks.contains(9))
                winner = 1
            else if (player1Clicks.contains(1) && player1Clicks.contains(4) && player1Clicks.contains(7))
                winner = 1
            else if (player1Clicks.contains(2) && player1Clicks.contains(5) && player1Clicks.contains(8))
                winner = 1
            else if (player1Clicks.contains(3) && player1Clicks.contains(6) && player1Clicks.contains(9))
                winner = 1
            else if (player1Clicks.contains(1) && player1Clicks.contains(5) && player1Clicks.contains(9))
                winner = 1
            else if (player1Clicks.contains(3) && player1Clicks.contains(5) && player1Clicks.contains(7))
                winner = 1
            else if (count == 9) {
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()
                restart()
            }
        } else if (whichPlayer == 2) {
            if (player2Clicks.contains(1) && player2Clicks.contains(2) && player2Clicks.contains(3))
                winner = 2
            else if (player2Clicks.contains(4) && player2Clicks.contains(5) && player2Clicks.contains(6))
                winner = 2
            else if (player2Clicks.contains(7) && player2Clicks.contains(8) && player2Clicks.contains(9))
                winner = 2
            else if (player2Clicks.contains(1) && player2Clicks.contains(4) && player2Clicks.contains(7))
                winner = 2
            else if (player2Clicks.contains(2) && player2Clicks.contains(5) && player2Clicks.contains(8))
                winner = 2
            else if (player2Clicks.contains(3) && player2Clicks.contains(6) && player2Clicks.contains(9))
                winner = 2
            else if (player2Clicks.contains(1) && player2Clicks.contains(5) && player2Clicks.contains(9))
                winner = 2
            else if (player2Clicks.contains(3) && player2Clicks.contains(5) && player2Clicks.contains(7))
                winner = 2
        }
        if (winner == 1) {
            Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show()
            player1Record += 1
            restart()
        } else if (winner == 2) {
            Toast.makeText(this, "Player 2 Won", Toast.LENGTH_SHORT).show()
            player2Record += 1
            restart()
        }
    }

    fun restart() {
        var clearAllButton: Button?
        player1Clicks.clear()
        player2Clicks.clear()
        for (i in 1..9) {
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
            count = 0
        }
        scoreCard = findViewById(R.id.win_records)
        scoreCard.text = "Player 1 : $player1Record\nPlayer 2 : $player2Record"
    }

    fun vsPlayer(view: View) {}
}