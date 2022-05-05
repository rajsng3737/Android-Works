package game.first.tictactoe

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class VersusOnline : AppCompatActivity() {
    var myEmail = ""
    var otherPlayer: String? = null
    private val database = FirebaseDatabase.getInstance("https://tictactoe8088-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val myRef = database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        myEmail = intent.getStringExtra("Email").toString()
        myRef.child("Users").child(myEmail.split('@')[0]).child("requests").setValue("null")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_versus_online)
        listenRequests()
    }
    fun sendRequest(view: View){
        otherPlayer = findViewById<EditText>(R.id.otherPlayer).text.toString().split('@')[0]
        val progressBar = findViewById<ProgressBar>(R.id.progressRequestingOrAccepting)
        progressBar.visibility = View.VISIBLE
        myRef.child("Users").child(otherPlayer!!).child("requests").setValue(myEmail.split("@")[0]).addOnCompleteListener(this){
            task->
            if(task.isSuccessful){
                progressBar.visibility=View.GONE
                Toast.makeText(applicationContext,"Request sent Successfully",Toast.LENGTH_LONG).show()
            }
            else{
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext,"Error Sending Request",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun acceptInvite(view:View) {
        val progressBar = findViewById<ProgressBar>(R.id.progressRequestingOrAccepting)
        progressBar.visibility = View.VISIBLE
        val incomingRequest = findViewById<EditText>(R.id.incomingRequestEmail)
        myRef.child("Users").child(incomingRequest.text.toString()).child("requests")
            .setValue("accepted").addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Request successfully Accepted", Toast.LENGTH_LONG).show()
                myRef.child("Users").child(myEmail.split('@')[0]).child("requests").setValue("null")
                createSession(incomingRequest.text.toString() + myEmail.split('@')[0],2)
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Request Cannot Be Accepted", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun listenRequests(){
        val incomingRequest = findViewById<EditText>(R.id.incomingRequestEmail)
        myRef.child("Users").child(myEmail.split('@')[0]).child("requests").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val td = snapshot.value
                Log.d(TAG, "onDataChange:$td")
                when {
                    td == "accepted" -> {
                        myRef.child("Users").child(myEmail.split('@')[0]).child("requests").setValue("null")
                        createSession(myEmail.split('@')[0] + otherPlayer,1)
                    }
                    td == "null" -> {
                        findViewById<TextView>(R.id.incomingRequestNotification).visibility = View.GONE
                        incomingRequest.visibility = View.GONE
                        findViewById<Button>(R.id.acceptInvite).visibility = View.GONE
                    }
                    td != "null" -> {
                        Log.d(TAG, "onDataChange: $td")
                        incomingRequest.setText(td.toString())
                        findViewById<TextView>(R.id.incomingRequestNotification).visibility = View.VISIBLE
                        incomingRequest.visibility = View.VISIBLE
                        findViewById<Button>(R.id.acceptInvite).visibility = View.VISIBLE
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun createSession(sessionId:String, playerValue : Int){
        myRef.child("Users").child("GameSessions").child(sessionId).child("whichPlayer").setValue(1)
        myRef.child("Users").child("GameSessions").child(sessionId).child("ButtonClicked").setValue(-1)
        myRef.child("Users").child("GameSessions").child(sessionId).child("Draw").setValue("null")
        myRef.child("Users").child("GameSessions").child(sessionId).child("someoneWon").setValue(-1)

        val intent = Intent(this,OnlineGame::class.java)
        intent.putExtra("sessionId",sessionId)
        intent.putExtra("playerValue",playerValue)
        startActivity(intent)
    }
}