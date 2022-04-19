package game.first.tictactoe

import android.content.ContentValues.TAG
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OnlineGame : AppCompatActivity() {
    private var player1Record = 0
    private var player2Record = 0
    private var myValue:Int ?= null
    private lateinit var scoreCard: TextView
    private var myClicks = ArrayList<Int>()
    private var choices = arrayListOf<Int>(1,2,3,4,5,6,7,8,9)
    private var count = 0
    private val database = FirebaseDatabase.getInstance("https://tictactoe8088-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val myRef = database.reference
    private var sessionId:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        sessionId = intent.getStringExtra("sessionId").toString()
        myValue = intent.getIntExtra("playerValue",10)
        if(myValue == 2)
            enableOrDisable(false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game)
        listenToChangeColors()
    }

    private fun enableOrDisable(trueOrfalse: Boolean){
        Log.d(TAG, "enableOrDisable: myValue = 2")
        for(i in choices){
            when(i){
                1 -> findViewById<Button>(R.id.button1)?.isClickable = trueOrfalse
                2 -> findViewById<Button>(R.id.button2)?.isClickable = trueOrfalse
                3 -> findViewById<Button>(R.id.button3)?.isClickable = trueOrfalse
                4 -> findViewById<Button>(R.id.button4)?.isClickable = trueOrfalse
                5 -> findViewById<Button>(R.id.button5)?.isClickable = trueOrfalse
                6 -> findViewById<Button>(R.id.button6)?.isClickable = trueOrfalse
                7 -> findViewById<Button>(R.id.button7)?.isClickable = trueOrfalse
                8 -> findViewById<Button>(R.id.button8)?.isClickable = trueOrfalse
                9 -> findViewById<Button>(R.id.button9)?.isClickable = trueOrfalse
            }
        }
    }

    private fun listenToChangeColors() {
        var whoClicked:Int ?=  null
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("ButtonClicked")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val td = snapshot.value.toString().toInt()  /* td = buttonClicked*/
                    if(td.equals(1..9)){
                        Log.d(TAG, "onDataChange: condition is true")
                        when(td){
                            1 -> findViewById<Button>(R.id.button1)?.isClickable = false
                            2 -> findViewById<Button>(R.id.button2)?.isClickable = false
                            3 -> findViewById<Button>(R.id.button3)?.isClickable = false
                            4 -> findViewById<Button>(R.id.button4)?.isClickable = false
                            5 -> findViewById<Button>(R.id.button5)?.isClickable = false
                            6 -> findViewById<Button>(R.id.button6)?.isClickable = false
                            7 -> findViewById<Button>(R.id.button7)?.isClickable = false
                            8 -> findViewById<Button>(R.id.button8)?.isClickable = false
                            9 -> findViewById<Button>(R.id.button9)?.isClickable = false
                        }
                        choices.remove(td)
                        drawOnButton(td)
                    }
                }
                private fun drawOnButton(buttonValue: Int) {
                    myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer")
                        .addListenerForSingleValueEvent(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                whoClicked = snapshot.value.toString().toInt()
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    when(buttonValue){
                        1 -> draw(findViewById(R.id.button1),whoClicked)
                        2 -> draw(findViewById(R.id.button2),whoClicked)
                        3 -> draw(findViewById(R.id.button3),whoClicked)
                        4 -> draw(findViewById(R.id.button4),whoClicked)
                        5 -> draw(findViewById(R.id.button5),whoClicked)
                        6 -> draw(findViewById(R.id.button6),whoClicked)
                        7 -> draw(findViewById(R.id.button7),whoClicked)
                        8 -> draw(findViewById(R.id.button8),whoClicked)
                        9 -> draw(findViewById(R.id.button9),whoClicked)
                    }
                    if(whoClicked != myValue)
                        enableOrDisable(true)
                    else
                        enableOrDisable(false)
                    if(myValue == 1)
                    {
                        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").setValue(2)
                    }
                    else{
                        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").setValue(1)
                    }
                }

                private fun draw(buttonToDraw: Button?, whoClicked: Int?) {
                    if(whoClicked == 1){
                        buttonToDraw?.setBackgroundColor(resources.getColor(R.color.player1Color))
                        buttonToDraw?.text = "X"
                    }
                    else{
                        buttonToDraw?.setBackgroundColor(resources.getColor(R.color.player2Color))
                        buttonToDraw?.text = "O"
                    }
                    return
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

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
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("ButtonClicked").setValue(buttonId)
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").setValue(myValue)
        myClicks.add(buttonId)
        checkWin()
        count += 1
    }

    private fun checkWin() {
        val result = calculateWin(myClicks)
        if ( result == 1) {
            Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show()
            player1Record += 1
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

    private fun restart() {
        var clearAllButton: Button?
        myClicks.clear()
        myClicks.clear()
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
            clearAllButton!!.setBackgroundColor(resources.getColor(R.color.teal_200,null))
            clearAllButton.text = ""
            clearAllButton.isClickable = true
            count = 0
        }
        scoreCard = findViewById(R.id.win_records)
        scoreCard.text = "Player 1 : $player1Record\nPlayer 2 : $player2Record"
    }
}