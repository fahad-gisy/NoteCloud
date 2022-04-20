package com.example.notecloud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var recycleNote:RecyclerView? = null
    private val databaseRef = Firebase.database.reference.child("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectVs()
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
 //       val email = user?.email // يجيب الايميل من الفايربيس
        try {
            userNoteArray(id)
        } catch (e: NullPointerException){

        }
       val addNoteBtn = findViewById<FloatingActionButton>(R.id.addnoteBTN)
        addNoteBtn.setOnClickListener {
            val fm = supportFragmentManager
            val fragmentTrans = fm.beginTransaction()
            fragmentTrans.replace(R.id.Fcontainer,Addnotefrag()).commit()
        }
    }

    private fun connectVs() {
       recycleNote = findViewById(R.id.recycleViewNote)
    }

    private fun userNoteArray(id:String?) {
        val notesRef = databaseRef.child(id!!).child("notes")
        val userNoteArray = ArrayList<NoteData>()
//        val customAdapter = CustomAdaptor(userNoteArray, this)
//        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
//        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
//        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
//        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
//        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
//        recycleNote?.adapter = customAdaptor

        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userNoteArray.clear() //لاحظت ان الكارد بتتكرر كل ما اضيف نوت جديدة وهذا السطر حل المشكلة
                    for (ds in snapshot.children) {
                            val note = ds.getValue(NoteData::class.java)
                            userNoteArray.add(note!!)
                    }
                    val customAdaptor = CustomAdaptor(userNoteArray, this@MainActivity)
                    recycleNote?.adapter = customAdaptor
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled")
            }
        }) // قراءة البيانات وعرضها ب recyclerView رابط الفيديو
        // https://www.youtube.com/watch?v=VVXKVFyYQdQ

    }



//        if (intent.getStringExtra("title") !=null){
//            val title = intent.getStringExtra("title")
//            val subTitle = intent.getStringExtra("subtitle")
//            val desc = intent.getStringExtra("desc")
//
//            val newStory = NoteData(title!!,desc!!,Date())
//
//            userNoteArray.add(newStory)
//
//            customAdaptor.notifyDataSetChanged()
//        }

}