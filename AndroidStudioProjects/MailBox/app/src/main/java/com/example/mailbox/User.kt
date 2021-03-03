package com.example.mailbox

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name:String, val password:String):Parcelable