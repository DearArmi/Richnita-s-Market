package com.test.supermarket

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Item")
@Parcelize
data class Item(@PrimaryKey val id:String,
                val name:String,
                val price:Double,
                /*val thumbnail:Bitmap*/):Parcelable