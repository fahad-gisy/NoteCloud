package com.example.notecloud

import java.util.*

data class NoteData(var title: String, var userNote:String, var currentTime: Date = Calendar.getInstance().time) {
}