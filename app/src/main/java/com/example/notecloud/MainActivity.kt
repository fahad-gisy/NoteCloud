package com.example.notecloud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var recycleNote:RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectVs()
        userNoteArray()
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

    private fun userNoteArray() {
       val userNoteArray = ArrayList<NoteData>()
        val customAdaptor = CustomAdaptor(userNoteArray,this)
        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
        userNoteArray.add(NoteData("title","this how i want my note look like",Date()))
        recycleNote?.adapter = customAdaptor


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
}