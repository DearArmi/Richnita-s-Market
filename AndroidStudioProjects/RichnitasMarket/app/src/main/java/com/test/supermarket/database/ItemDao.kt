package com.test.supermarket.database

import androidx.room.*
import com.test.supermarket.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(itemList:MutableList<Item>)

   @Query("SELECT COUNT(id) FROM item")
    fun countRows(): Int

    @Query("SELECT * FROM Item WHERE id = :id")
    fun getItem(id:String):Item

}