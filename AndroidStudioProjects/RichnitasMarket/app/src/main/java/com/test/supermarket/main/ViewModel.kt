package com.test.supermarket.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.supermarket.Item
import com.test.supermarket.database.getDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var item: Item
    val list = mutableListOf<Item>()

    var totalPrice = 0.0

    private val dataBase = getDataBase(application)
    val mainRepository = Repository(dataBase)

    private var _itemList = MutableLiveData<MutableList<Item>>()
    val itemList: LiveData<MutableList<Item>>
        get() = _itemList

    private var _total = MutableLiveData<Double>()
    val total: LiveData<Double>
        get() = _total

    init {
        checkDataBase()
        _total.value = 0.0
    }

    private fun checkDataBase() {
        //Verifying Empty database
        viewModelScope.launch(Dispatchers.IO) {
            if (mainRepository.checkDataBase() == 0){
                mainRepository.getItemsFromApi()
            }else{
                /////Gets data from Database
            }
        }

    }

    fun removeItem(item: Item){
        viewModelScope.launch {
            list.remove(item)
            _itemList.value = list
            //Subtraction to total
            totalPrice-=item.price
            _total.value = totalPrice
        }
    }

    fun addItem(id: String){
        //Getting Item from DataBase and adding to list
        viewModelScope.launch {

            item = mainRepository.getItemFromDB(id)
            list.add(item)
            _itemList.value = list
            //adding to total
            totalPrice+=item.price
            _total.value = totalPrice

        }
    }

}