package com.example.notecloud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btnRegister = findViewById<Button>(R.id.regestarBTN)
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
         var email = findViewById<EditText>(R.id.EmailED).text.toString()
         var passWord = findViewById<EditText>(R.id.EmailED).text.toString()
        if (email.isNotEmpty() && passWord.isNotEmpty()){ // انشاء تسجيل دخول اميل و باسورد!! في فاير بيس
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passWord)
                .addOnCompleteListener { task ->
                         if (task.isSuccessful){ // لو تم تسجيل الدخول
                             val fireBaseUser : FirebaseUser = task.result!!.user!!
                             Toast.makeText(this,"تم التسجيل بنجاح",Toast.LENGTH_SHORT).show()
                             var intent = Intent(this,MainActivity::class.java)
                             intent.putExtra("user_id",fireBaseUser.uid)
                             intent.putExtra("email_id",email)
                             startActivity(intent)
                             finish()
                         }else{
                             Toast.makeText(this,"لم يتم تسجيل الدخول حاول مرة اخرى",Toast.LENGTH_SHORT).show()
                         }
                 }
        }
    }


}