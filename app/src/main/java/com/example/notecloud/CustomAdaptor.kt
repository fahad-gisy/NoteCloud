package com.example.notecloud

import android.R.attr
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import android.R.attr.data




class CustomAdaptor(val noteArrayList: ArrayList<NoteData>, context: Context):RecyclerView.Adapter<CustomAdaptor.DataHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.customnotelayout, parent, false)
        )

    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val currentTime = Calendar.getInstance().time
           val noteData = noteArrayList[position]
           holder.noteTitle.text = noteData.title
           holder.wholeNote.text = noteData.userNote
          holder.recycleDate.text = noteData.currentTime.toString()
        backCardNoteColor(holder, position)
    }

    private fun backCardNoteColor(holder:DataHolder,position: Int) {
        val r  = java.util.Random()
        val red = r.nextInt(150+position)
        val green = r.nextInt(150-position+1)
        val blue = r.nextInt(150+position+1)
        holder.noteBackColor.setCardBackgroundColor(Color.rgb(red,green,blue))
    }

    override fun getItemCount(): Int {

        return noteArrayList.size

    }

    class DataHolder(item:View):RecyclerView.ViewHolder(item){
       val noteTitle:TextView = item.findViewById(R.id.RecycleTitle)
       val wholeNote:TextView = item.findViewById(R.id.RecycleNote)
       val recycleDate:TextView = item.findViewById(R.id.RecycleDate)
        val noteBackColor:CardView = item.findViewById(R.id.noteBackColor)
    }
}


