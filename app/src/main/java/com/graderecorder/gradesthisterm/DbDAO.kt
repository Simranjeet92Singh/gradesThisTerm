package com.graderecorder.gradesthisterm

import androidx.room.*


@Dao
interface DbDAO {
    @Insert
    fun addData(dataModels: DataModels?)

    @Delete
    fun delete(dataModels: DataModels?)

    @Query("select * from `GradesAppDatabase`")
    fun getAllData(): List<DataModels>


    @Query("delete from `GradesAppDatabase`")
    fun deleteAll()
}