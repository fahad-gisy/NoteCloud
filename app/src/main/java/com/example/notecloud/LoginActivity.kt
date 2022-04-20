package com.example.notecloud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.katso.livebutton.LiveButton
private val databaseRef = Firebase.database.reference.child("users")
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
         var fName = findViewById<EditText>(R.id.usernameED).text.toString()
         var email = findViewById<EditText>(R.id.EmailED).text.toString()
         var passWord = findViewById<EditText>(R.id.passwordED).text.toString()
         val user = User(fName, email) // كلاس تاخذ بيانات اليوزر مثل:(الاسم الاول/ الاسم الاخير /تاريخ الميلاد /..الخ) | ممكن نستغني عن الكلاس ونضيف المعلومات بشكل فردي
        if (email.isNotEmpty() && passWord.isNotEmpty()){ // انشاء تسجيل دخول اميل و باسورد!! في فاير بيس
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,passWord)
                .addOnCompleteListener { task ->
                         if (task.isSuccessful){ // لو تم تسجيل الدخول
                             val fireBaseUser : FirebaseUser = task.result!!.user!!
                             Toast.makeText(this,"تم التسجيل بنجاح",Toast.LENGTH_SHORT).show()
                             var intent = Intent(this,MainActivity::class.java)
//                             intent.putExtra("user_id",fireBaseUser.uid)
                             intent.putExtra("email_id",email)
                             saveUser(user, fireBaseUser.uid) // دالة تحفظ بيانات اليوزر بال id في فايربيس
                             startActivity(intent)
                             finish()
                         }else{
                             Toast.makeText(this,"لم يتم تسجيل الدخول حاول مرة اخرى",Toast.LENGTH_SHORT).show()
                         }
                 }
        }
    }

    private fun saveUser(user: User, id: String) {
        val userRef = databaseRef.child(id)
        userRef.setValue(user)
    }



}