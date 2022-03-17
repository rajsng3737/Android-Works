package com.example.thegame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

class theOption : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_option)
    }

    fun vsPlayer(view: View) {
        intent = Intent(this, VersusPlayer::class.java)
        startActivity(intent)
    }

    fun vsComputer(view: View) {
        intent = Intent(this, VersusComputer::class.java)
        startActivity(intent)
    }

    fun vsFriend(view : View){
        intent = Intent(this,VersusFriend::class.java)
        startActivity(intent);
    }
}