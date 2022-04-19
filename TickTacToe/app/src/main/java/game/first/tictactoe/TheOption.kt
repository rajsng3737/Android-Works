package game.first.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class TheOption : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    private var email = ""
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        email = intent.getStringExtra("Email").toString()
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_the_option)
    }
    fun vsOnline(view: View){
        intent = Intent(this, VersusOnline::class.java)
        intent.putExtra("Email",email)
        startActivity(intent)
    }

    fun vsPlayer(view: View) {
        intent = Intent(this, VersusPlayer::class.java)
        startActivity(intent)
    }

    fun vsComputer(view: View) {
        intent = Intent(this, VersusComputer::class.java)
        startActivity(intent)
    }

    fun signOut(item: MenuItem) {
        mAuth!!.signOut()
        startActivity(parentActivityIntent)
        finish()
    }
}