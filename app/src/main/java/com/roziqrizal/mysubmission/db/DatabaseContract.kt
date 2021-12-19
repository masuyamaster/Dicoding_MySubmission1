package com.roziqrizal.mysubmission.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "userFavourite"
            const val ID = "_id"
            const val LOGIN = "login"
            const val REPOSITORY = "repository"
            const val IMAGE = "image"
        }
    }
}