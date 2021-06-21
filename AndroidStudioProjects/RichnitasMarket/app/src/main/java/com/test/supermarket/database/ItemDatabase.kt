package com.test.supermarket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.supermarket.Item

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class ItemDatabase:RoomDatabase() {
    abstract val itemDao:ItemDao
}


private lateinit var INSTANCE: ItemDatabase

fun getDataBase(context: Context):ItemDatabase{

    synchronized(ItemDatabase::class.java){

        if (!::INSTANCE.isInitialized){

            INSTANCE = Room.databaseBuilder(context.applicationContext, ItemDatabase::class.java, "item_db")
                .build()

        }
        return INSTANCE
    }

}