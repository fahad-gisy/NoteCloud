package com.example.notecloud

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TransformationMethod
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.katso.livebutton.LiveButton

var registerCard:CardView? = null
var linearLayoutRegister:LinearLayout? = null
var linearLayoutLogin:LinearLayout? = null
var newaccontCard:CardView? = null
var loginCard:CardView? = null
var loginButton:LiveButton? = null
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        connectVs()
        userlogined()
        expandRegisterCard()
        loginFun()


        var btnRegister = findViewById<LiveButton>(R.id.regestarBTN)
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun userlogined() {
      val  auth  = FirebaseAuth.getInstance()
        if (auth.currentUser !=null){
            startActivity(Intent(this,MainActivity().javaClass))
            finish()
        }
    }

    private fun loginFun() {
        loginButton?.setOnClickListener {
            var email = findViewById<EditText>(R.id.EmailL).text.toString()
            var passWord = findViewById<EditText>(R.id.passwordL).text.toString()
            if (email.isNotEmpty() && passWord.isNotEmpty()){ // انشاء تسجيل دخول اميل و باسورد!! في فاير بيس
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,passWord)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){ // لو تم تسجيل الدخول
                            val fireBaseUser : FirebaseUser = task.result!!.user!!
                            Toast.makeText(this,"تم الدخول بنجاح",Toast.LENGTH_SHORT).show()
                            var intent = Intent(this,MainActivity::class.java)
                            intent.putExtra("user_id",fireBaseUser.uid)
                            intent.putExtra("email_id",email)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this,"لم يتم  الدخول حاول مرة اخرى",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


    private fun connectVs() {
        linearLayoutRegister = findViewById(R.id.layout)
        linearLayoutLogin = findViewById(R.id.layoutL)
        registerCard = findViewById(R.id.registerScreen)
        newaccontCard = findViewById(R.id.newaccont)
        loginCard = findViewById(R.id.loginaccont)
        loginButton = findViewById(R.id.LoginBTN)
    }

    private fun expandRegisterCard() {

         loginCard?.setOnClickListener {
             if(linearLayoutLogin?.visibility == View.GONE){
                 linearLayoutLogin!!.visibility = View.VISIBLE
             }

         }

           newaccontCard?.setOnClickListener {
               if (linearLayoutRegister?.visibility == View.GONE){
                    linearLayoutRegister!!.visibility = View.VISIBLE

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