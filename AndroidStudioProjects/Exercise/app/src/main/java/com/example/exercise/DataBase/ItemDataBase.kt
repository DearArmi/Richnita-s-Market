package com.example.exercise.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exercise.Item


@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase(){
    abstract val itemDao: ItemDao
}

    private lateinit var INSTANCE: ItemDatabase

    fun getDataBase(context: Context):ItemDatabase {
        synchronized(ItemDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, ItemDatabase::class.java, "earthquake_db").build()
            }
            return INSTANCE
        }

    }
