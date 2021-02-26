package com.example.earthquakemonitor

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize @Entity(tableName = "earthqueake")
data class Earthquake(@PrimaryKey val id:String, val place:String, val magnitude:Double, val time:Long, val longitude:Double, val latitude:Double):Parcelable
