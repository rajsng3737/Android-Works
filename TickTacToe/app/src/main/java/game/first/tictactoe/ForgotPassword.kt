package game.first.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class ForgotPassword : AppCompatActivity() {
    private var mAuth:FirebaseAuth ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        mAuth = FirebaseAuth.getInstance()
    }
    fun forgotPassword(view: View){
        val email = findViewById<EditText>(R.id.fgPasswordEnterEmail).text.toString()
        mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener(this){ task ->
                if (task.isSuccessful)
                    Toast.makeText(applicationContext,"Sign in Successful", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                var backButton = findViewById<TextView>(R.id.backToHome)
                var resetToast = findViewById<TextView>(R.id.resetPasswordToast)
                backButton.visibility = View.VISIBLE
                backButton.isClickable = true
                resetToast.visibility = View.VISIBLE
                resetToast.isClickable = false
        }
    }
    fun backToHome(view : View){
        val intent = Intent(this,SignIn::class.java)
        startActivity(intent)
    }
}