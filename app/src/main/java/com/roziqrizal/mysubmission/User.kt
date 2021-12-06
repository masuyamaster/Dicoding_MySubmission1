package com.roziqrizal.mysubmission

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var user_github: Int,
    var username: String,
    var avatar: String,
    var company: String,
    var location: String,
    var repository: String,
    var follower: String,
    var following: String
) : Parcelable