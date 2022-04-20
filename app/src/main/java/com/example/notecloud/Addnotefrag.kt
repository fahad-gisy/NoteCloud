package com.example.notecloud

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.katso.livebutton.LiveButton
import java.lang.NullPointerException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class Addnotefrag : Fragment() {
var floatBtn:FloatingActionButton? = null
var titleEd:EditText? = null
var wholeNote:EditText? = null
var liveBtn:LiveButton? = null
//var database:FirebaseDatabase? = null
var maxid:Long = 0
private val databaseRef = Firebase.database.reference.child("users")

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
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid
        try {
            saveToDatabase(id)
        } catch (e: NullPointerException){

        }
        databaseRef.child(id!!).child("notes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists())  // شرط يتأكد من عدم تكرار النوت id عن طريق حساب عدد النوتس الموجودة
                    maxid = (dataSnapshot.childrenCount)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("error", "loadPost:onCancelled", databaseError.toException())
            }
        }) // دالة تقرأ البيانات من الفايربيس بالتحديد داخل المسار child("notes") رابط الفيديو:
    }      // https://www.youtube.com/watch?v=r-g2R_COMqo



    private fun connectVs(view: View) {
        floatBtn = view.findViewById(R.id.floatingSaveBtn)
      titleEd = view.findViewById(R.id.title)
      wholeNote = view.findViewById(R.id.note)
      liveBtn = view.findViewById(R.id.SaveBTN)
    }

    private fun saveToDatabase(id:String?) {
//        val mRef:DatabaseReference = database!!.reference
            val notesRef= databaseRef.child(id!!).child("notes")
        floatBtn?.setOnClickListener {
            val title = titleEd?.text.toString()
            val notes = wholeNote?.text.toString()
            val noteData = NoteData(title,notes, Date()) //Date = Calendar.getInstance().time
            val key = maxid+1 //ياخد عدد النوت الموجودة من دالة الوفرايد ويضيف عليه 1 توضيح: اذا كان عدد النوت في الداتابيس 2 يصبح رقم النوت التالية (1+2) اي 3 وهكذا
            notesRef.child("note$key").setValue(noteData) //  دالة تحفظ بيانات النوت بالفايربيس وكل نوت لها id توضيح:(note1/ note2/ note3)
//            noteRef.child("notes").setValue(noteData)
        }
    }


}