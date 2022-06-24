package com.graderecorder.gradesthisterm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DbDAO {
    @Insert
    fun addData(dataModels: DataModels?)

    @Delete
    fun delete(dataModels: DataModels?)

    @Query("delete from `GradesAppDatabase`")
    fun deleteAll()
}