package com.example.earthquakemonitor.Main

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.*
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.ApiResponseStatus
import com.example.earthquakemonitor.database.getDataBAse
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val Tag = MainViewModel::class.java.simpleName

class MainViewModel(application: Application, private val sortType:Boolean): AndroidViewModel(application) {

    private val dataBase = getDataBAse(application)
    val mainRepository = MainRepository(dataBase)

    private var _status = MutableLiveData<ApiResponseStatus>()
    val status: MutableLiveData<ApiResponseStatus>
        get() = _status

    private var _eqlist = MutableLiveData<MutableList<Earthquake>>()
    val eqlist: LiveData<MutableList<Earthquake>>
        get() = _eqlist

    init {

        reloadEarquakes()

    }

    private fun reloadEarquakes() {
        viewModelScope.launch {

            try {
                _status.value = ApiResponseStatus.LOADING
                _eqlist.value = mainRepository.fetchEarthQuakes(sortType)
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.ERROR
                Log.d(Tag, "No internet", e)
            }

        }
    }

    fun reloadEarquakesFromDb(sortByMagnitude: Boolean) {

        viewModelScope.launch {
            _eqlist.value = mainRepository.fetchEarthQuakesFromDb(sortByMagnitude)

        }
    }
}



