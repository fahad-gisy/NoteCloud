package com.example.notecloud

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

var registerCard:CardView? = null
var linearLayoutRegister:LinearLayout? = null
var newaccontCard:CardView? = null
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        connectVs()
        expandRegisterCard()
        var btnRegister = findViewById<Button>(R.id.regestarBTN)
        btnRegister.setOnClickListener {
            registerUser()
        }
    }



    private fun connectVs() {
        linearLayoutRegister = findViewById(R.id.layout)
        registerCard = findViewById(R.id.registerScreen)
        newaccontCard = findViewById(R.id.newaccont)
    }

    private fun expandRegisterCard() {
           newaccontCard?.setOnClickListener {
               if (newaccontCard?.isSelected == true){
                   registerCard?.visibility == View.VISIBLE
               }else{
                   registerCard?.visibility == View.GONE
               }
           }
    }


    private fun registerUser() {
         var email = findViewById<EditText>(R.id.EmailED).text.toString()
         var passWord = findViewById<EditText>(R.id.passwordED).text.toString()
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