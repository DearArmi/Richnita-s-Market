package com.example.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.earthquakemonitor.Earthquake

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("SELECT * FROM earthqueake")
    fun getEarqueakes(): MutableList<Earthquake>

    @Query("SELECT * FROM earthqueake")
    fun getEarqueakes2(): LiveData<MutableList<Earthquake>>

    @Query("SELECT * FROM earthqueake order by magnitude ASC")
    fun getEarqueakesMAg(): MutableList<Earthquake>

    @Query("SELECT * FROM earthqueake WHERE magnitude > :mag")
    fun getMagnitudeEarqueake(mag:Double): MutableList<Earthquake>
}