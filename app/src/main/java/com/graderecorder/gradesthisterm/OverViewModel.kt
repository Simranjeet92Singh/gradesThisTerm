package com.graderecorder.gradesthisterm

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable



data class OverViewModel (
    val cName:String?="",
    val gradesSubmitted:Double?=0.0,
    val gradesToDate:Double?=0.0
)