package com.example.earthquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.earthquakemonitor.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDatabase:RoomDatabase(){
    abstract val eqDao: EqDao
}

private lateinit var INTANCE: EqDatabase

fun getDataBAse(context: Context):EqDatabase{
    synchronized(EqDatabase::class.java){
        if (!::INTANCE.isInitialized){
            INTANCE = Room.databaseBuilder(context.applicationContext,EqDatabase::class.java, "earthquake_db").build()
        }
        return INTANCE
    }
}