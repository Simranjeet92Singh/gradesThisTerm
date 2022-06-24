package com.graderecorder.gradesthisterm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DataModels::class], version = 2, exportSchema = false)
abstract class Db : RoomDatabase() {
    abstract fun dbDAO():DbDAO
    companion object{
        @Volatile
        private var instance:Db?=null
        @Synchronized
        fun getInstance(ctx: Context):Db{
            if (instance==null){
                instance= Room.databaseBuilder(ctx.applicationContext,Db::class.java,"GradesAppDatabase")
                    .fallbackToDestructiveMigration()
                    .build()

                return instance!!

            }
            return instance!!
        }


    }
}