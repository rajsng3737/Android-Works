package game.first.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mAuth = FirebaseAuth.getInstance()
    }
    fun signIn(view: View){
        val email = findViewById<EditText>(R.id.EmailAddress).text.toString()
        val password = findViewById<EditText>(R.id.Password).text.toString()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Sign in Successful", Toast.LENGTH_LONG).show()
                loadActivity()
            }
            else{
                Toast.makeText(applicationContext,"Sign in Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun createAccount(view : View)
    {
        var intent = Intent(this, CreateAccount::class.java)
        startActivity(intent)
    }

    private fun loadActivity(){
        val user = mAuth.currentUser
        if(user!=null){
            var intent = Intent(this,TheOption::class.java)
            intent.putExtra("Email",user.email)
            intent.putExtra("uid",user.uid)
            intent.putExtra("user",user)
            startActivity(intent)
        }
    }
}