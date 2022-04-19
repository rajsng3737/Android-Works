package game.first.tictactoe


import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccount : AppCompatActivity() {
    private var mAuth:FirebaseAuth ?= null
    private val database = FirebaseDatabase.getInstance("https://tictactoe8088-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val myRef = database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance()
    }
    fun createAccount(view: View){
        val progressBar = findViewById<ProgressBar>(R.id.createAccountProgressBar)
        progressBar.visibility = View.VISIBLE
        val email = findViewById<EditText>(R.id.EmailAddress).text.toString()
        val password = findViewById<EditText>(R.id.Password).text.toString()
        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Account Created Successfully! Log In",Toast.LENGTH_LONG).show()
                myRef.child("Users").child(email.split('@')[0]).child("requests").setValue("null")
                progressBar.visibility = View.GONE
                mAuth!!.signOut()
                signInActivity()
            }
            else{
                Toast.makeText(applicationContext,"Account Creation Failed",Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        }
    }
    fun signIn(view : View){
        var intent = Intent(this,SignIn::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        loadActivity()
    }
    private fun signInActivity(){
        var intent = Intent(this,SignIn::class.java)
        startActivity(intent)
    }
    private fun loadActivity(){
        val user = mAuth!!.currentUser
        if(user!=null){
            var intent = Intent(this,TheOption::class.java)
            intent.putExtra("Email",user.email)
            intent.putExtra("uid",user.uid)
            intent.putExtra("user",user)
            startActivity(intent)
        }
    }
}
