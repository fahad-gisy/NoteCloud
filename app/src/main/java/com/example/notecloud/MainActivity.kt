package com.example.notecloud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val logoutButton = findViewById<Button>(R.id.logout)

        findViewById<TextView>(R.id.userID).text = userId
        findViewById<TextView>(R.id.emailID).text = emailId

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }
}