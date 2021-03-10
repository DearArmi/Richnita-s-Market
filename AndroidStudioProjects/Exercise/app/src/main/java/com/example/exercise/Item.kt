package com.example.exercise

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Item")
@Parcelize
data class Item(@PrimaryKey val id:Int, val listId:Int, val name:String):Parcelable