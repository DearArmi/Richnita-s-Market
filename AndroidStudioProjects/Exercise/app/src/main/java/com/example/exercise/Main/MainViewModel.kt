package com.example.exercise.Main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.exercise.Api.ApiResponseStatus
import com.example.exercise.DataBase.getDataBase
import com.example.exercise.Item
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //Getting Database
    val dataBase = getDataBase(application)
    val mainRepository = MainRepository(dataBase)

    private var _status = MutableLiveData<ApiResponseStatus>()
    val status: MutableLiveData<ApiResponseStatus>
        get() = _status

    private var _itemList = MutableLiveData<MutableList<Item>>()
    val itemList: LiveData<MutableList<Item>>
        get() = _itemList

    init {
        loadItems()
    }

    private fun loadItems() {

        viewModelScope.launch {
            try{
                _status.value = ApiResponseStatus.LOADING

                //Verifying rows in DB
                if(mainRepository.checkDataBase() > 0){
                    _itemList.value = mainRepository.fetchItemsFromDB()
                }else{
                    _itemList.value = mainRepository.fetchItems()
                }
                _status.value = ApiResponseStatus.DONE

            }catch (e: UnknownHostException){
                _status.value = ApiResponseStatus.ERROR
                Log.d("","No internet", e)

            }
        }


    }
}