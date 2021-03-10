package com.example.exercise.Main

import com.example.exercise.Api.service
import com.example.exercise.DataBase.ItemDatabase
import com.example.exercise.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class MainRepository(private val dataBase: ItemDatabase) {

    //Fetching using API
    suspend fun fetchItems(): MutableList<Item> {

        return withContext(Dispatchers.IO) {

            //Getting Json file and passing it to the parsing method
            val itemString = service.getItems()
            val itemList = parseItems(itemString)

            //DATABASE INSERTION
            dataBase.itemDao.insertAll(itemList)

            fetchItemsFromDB()
        }

    }

    //Fetching using DB
    suspend fun fetchItemsFromDB(): MutableList<Item>{

        return withContext(Dispatchers.IO){

            dataBase.itemDao.getOrderedItems()
        }
    }

    //Verifying if DB is empty
    suspend fun checkDataBase(): Int{

        return withContext(Dispatchers.IO){

            dataBase.itemDao.countRows()
        }
    }

    //Parsing JSON file from
    private fun parseItems(itemString: String): MutableList<Item> {

        val itemJsonObject = JSONArray(itemString)

        val itemList = mutableListOf<Item>()

        for (i in 0 until itemJsonObject.length()) {

            val featureJasonObject = itemJsonObject[i] as JSONObject
            val id = featureJasonObject.getInt("id")
            val listId = featureJasonObject.getInt("listId")
            val name = featureJasonObject.getString("name")

            //Verifying null and empty names
            if (name != "" && name !="null"){
                itemList.add(Item(id, listId, name))
            }

        }
        return itemList
    }
}


