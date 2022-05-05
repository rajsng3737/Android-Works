package game.first.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OnlineGame : AppCompatActivity() {
    private var myRecord = 0
    private var otherPlayerRecord = 0
    var playerSign:String = ""
    private var myValue:Int ?= null
    private lateinit var scoreCard: TextView
    private var myClicks = ArrayList<Int>()
    private var otherPlayerClicks = ArrayList<Int>()
    private var whoWon:Int = 0
    private var whichPlayer:Int = 0
    private var choices = arrayListOf<Int>(1,2,3,4,5,6,7,8,9)
    private var count = 0
    private val database = FirebaseDatabase.getInstance("https://tictactoe8088-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val myRef = database.reference
    private var sessionId:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        sessionId = intent.getStringExtra("sessionId").toString()
        myValue = intent.getIntExtra("playerValue",10)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game)
        if(myValue == 2)
            enabledOrDisabled(false)
        listenToChangeColors()
        whatTodraw()
        whichPlayerTurn()
        someoneWon()
    }
    private fun whatTodraw() {
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("Draw").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val td = snapshot.value.toString()
                playerSign = td
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun whichPlayerTurn(){
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val td = snapshot.value.toString().toInt()
                whichPlayer = td
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun enabledOrDisabled(trueOrfalse:Boolean){
        for(i in choices){
            when(i){
                1-> findViewById<Button>(R.id.onlineButton1).isClickable =  trueOrfalse
                2-> findViewById<Button>(R.id.onlineButton2).isClickable =  trueOrfalse
                3-> findViewById<Button>(R.id.onlineButton3).isClickable =  trueOrfalse
                4-> findViewById<Button>(R.id.onlineButton4).isClickable =  trueOrfalse
                5-> findViewById<Button>(R.id.onlineButton5).isClickable =  trueOrfalse
                6-> findViewById<Button>(R.id.onlineButton6).isClickable =  trueOrfalse
                7-> findViewById<Button>(R.id.onlineButton7).isClickable =  trueOrfalse
                8-> findViewById<Button>(R.id.onlineButton8).isClickable =  trueOrfalse
                9-> findViewById<Button>(R.id.onlineButton9).isClickable =  trueOrfalse
            }
        }
    }

    private fun listenToChangeColors() {
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("ButtonClicked").addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val td = snapshot.value.toString().toInt()
                    if(  td in 1..9 ){
                        when(td){
                            1 -> {
                                findViewById<Button>(R.id.onlineButton1)?.isClickable = false
                                draw(findViewById(R.id.onlineButton1), playerSign)
                            }
                            2 -> {
                                findViewById<Button>(R.id.onlineButton2)?.isClickable = false
                                draw(findViewById(R.id.onlineButton2), playerSign)
                            }
                            3 -> {
                                findViewById<Button>(R.id.onlineButton3)?.isClickable = false
                                draw(findViewById(R.id.onlineButton3), playerSign)
                            }
                            4 -> {
                                findViewById<Button>(R.id.onlineButton4)?.isClickable = false
                                draw(findViewById(R.id.onlineButton4),playerSign)
                            }
                            5 -> {
                                findViewById<Button>(R.id.onlineButton5)?.isClickable = false
                                draw(findViewById(R.id.onlineButton5), playerSign)
                            }
                            6 -> {
                                findViewById<Button>(R.id.onlineButton6)?.isClickable = false
                                draw(findViewById(R.id.onlineButton6),playerSign)
                            }
                            7 -> {
                                findViewById<Button>(R.id.onlineButton7)?.isClickable = false
                                draw(findViewById(R.id.onlineButton7),playerSign)
                            }
                            8 -> {
                                findViewById<Button>(R.id.onlineButton8)?.isClickable = false
                                draw(findViewById(R.id.onlineButton8),playerSign)
                            }
                            9 -> {
                                findViewById<Button>(R.id.onlineButton9)?.isClickable = false
                                draw(findViewById(R.id.onlineButton9),playerSign)
                            }
                        }
                        if(myValue == 1 && playerSign == "O")
                            otherPlayerClicks.add(td)
                        else if(myValue == 2 && playerSign == "X")
                            otherPlayerClicks.add(td)
                        choices.remove(td)
                        changeTurns()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun draw(buttonToDraw: Button?, whatSign: String) {
        if(whatSign == "X"){
            buttonToDraw?.setBackgroundColor(resources.getColor(R.color.player1Color))
            buttonToDraw?.text = "X"
        }
        else if (whatSign == "O"){
            buttonToDraw?.setBackgroundColor(resources.getColor(R.color.player2Color))
            buttonToDraw?.text = "O"
        }
    }
    private fun changeTurns(){
        if(whichPlayer == myValue)
            enabledOrDisabled(true)
        else
            enabledOrDisabled(false)
    }
    fun buttonClicked(view: View) {
        val whichButton = view as Button
        var buttonId = 0
        when (whichButton.id) {
            R.id.onlineButton1 -> buttonId = 1
            R.id.onlineButton2 -> buttonId = 2
            R.id.onlineButton3 -> buttonId = 3
            R.id.onlineButton4 -> buttonId = 4
            R.id.onlineButton5 -> buttonId = 5
            R.id.onlineButton6 -> buttonId = 6
            R.id.onlineButton7 -> buttonId = 7
            R.id.onlineButton8 -> buttonId = 8
            R.id.onlineButton9 -> buttonId = 9
        }
        if(myValue == 1)
        {
            myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("Draw").setValue("X")
            myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").setValue(2)

        }
        else
        {
            myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("Draw").setValue("O")
            myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("whichPlayer").setValue(1)
        }
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("ButtonClicked").setValue(buttonId)
        myClicks.add(buttonId)
        checkWin(myClicks, 1,false)
        count += 1
    }
    private fun someoneWon()
    {
        myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("someoneWon").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val td = snapshot.value.toString().toInt()
                whoWon = td
                checkWin(otherPlayerClicks,2,true)
                restart()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun checkWin(whoseClicks:ArrayList<Int>, whichPlayer: Int,listened:Boolean) {
        val result = calculateWin(whoseClicks)
        if (result == 1) {
            Toast.makeText(this, "Player $whichPlayer Won", Toast.LENGTH_SHORT).show()
            if(listened)
                otherPlayerRecord++;
            else{
                myRecord++;
                myRef.child("Users").child("GameSessions").child(sessionId.toString()).child("someoneWon").setValue(myValue)
            }
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
        choices.clear()
        myClicks.clear()
        otherPlayerClicks.clear()
        for (i in 1..9) {
            choices.add(i)
            clearAllButton = when (i) {
                1 -> findViewById(R.id.onlineButton1)
                2 -> findViewById(R.id.onlineButton2)
                3 -> findViewById(R.id.onlineButton3)
                4 -> findViewById(R.id.onlineButton4)
                5 -> findViewById(R.id.onlineButton5)
                6 -> findViewById(R.id.onlineButton6)
                7 -> findViewById(R.id.onlineButton7)
                8 -> findViewById(R.id.onlineButton8)
                9 -> findViewById(R.id.onlineButton9)
                else -> null
            }
            clearAllButton!!.setBackgroundColor(resources.getColor(R.color.teal_200,null))
            clearAllButton.text = ""
            clearAllButton.isClickable = true
            count = 0
        }
        if (myValue == 1)
        {
            myValue = 2
            enabledOrDisabled(false)
        }
        else if(myValue == 2)
        {
            myValue = 1
            enabledOrDisabled(true)
        }
        scoreCard = findViewById(R.id.win_records)
        scoreCard.text = "You: $myRecord\n Other : $otherPlayerRecord"
    }
}