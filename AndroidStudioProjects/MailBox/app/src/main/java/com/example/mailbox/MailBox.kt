package com.example.mailbox

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MailBox(val id:Int, val latitude:Double, val longitude:Double, val status:MailBoxStatus):Parcelable {

    enum class MailBoxStatus {
        LOCKED, UNLOCKED
    }

}