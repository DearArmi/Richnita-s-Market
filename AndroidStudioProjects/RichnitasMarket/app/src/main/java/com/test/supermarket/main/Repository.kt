package com.test.supermarket.main

import com.test.supermarket.Item
import com.test.supermarket.database.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dataBase: ItemDatabase) {

    //Fetching using DB
    suspend fun getItemFromDB(id:String): Item{

        return withContext(Dispatchers.IO){

            dataBase.itemDao.getItem(id)
        }
    }

    //Verifying if DB is empty
    fun checkDataBase(): Int{

        return dataBase.itemDao.countRows()

    }

    //"Downloading from API"
    fun getItemsFromApi(){
        val itemList = mutableListOf<Item>()

        itemList.add(Item("0001","Banana",1.00))
        itemList.add(Item("0002","Apple",4.00))
        itemList.add(Item("0003","Other Stuff",10.00))
        dataBase.itemDao.insertAll(itemList)
    }

}