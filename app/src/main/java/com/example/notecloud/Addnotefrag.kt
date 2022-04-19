package com.example.notecloud

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ru.katso.livebutton.LiveButton
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Addnotefrag : Fragment() {
var floatBtn:FloatingActionButton? = null
var titleEd:EditText? = null
var wholeNote:EditText? = null
var liveBtn:LiveButton? = null
var database:FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addnotefrag, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectVs(view)
        saveToDatabase()
    }



    private fun connectVs(view: View) {
        floatBtn = view.findViewById(R.id.floatingSaveBtn)
      titleEd = view.findViewById(R.id.title)
      wholeNote = view.findViewById(R.id.note)
      liveBtn = view.findViewById(R.id.SaveBTN)
    }
          //هنا حاولت ارفع للداتا بيس لكن م ترتفع معي في غلط في الموضوع
    private fun saveToDatabase() {
        database = FirebaseDatabase.getInstance()
        val mRef:DatabaseReference = database!!.reference
        floatBtn?.setOnClickListener {

            val title = titleEd?.text.toString()
            val notes = wholeNote?.text.toString()
            val noteData = NoteData(title,notes)
            mRef.child("notes").setValue(noteData)
        }
    }


}