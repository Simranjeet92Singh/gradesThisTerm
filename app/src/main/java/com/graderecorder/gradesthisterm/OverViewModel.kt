package com.graderecorder.gradesthisterm

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName="GradesAppDatabase")
data class OverViewModel (
    @PrimaryKey(autoGenerate = true)
    var key:Int=0,
    var courseNumber:String?="null",
    var totalGrades:Double?=0.0,
    var gradesReceived:Double?=0.0,
    var gradeType:String?="null",

):Serializable{

}