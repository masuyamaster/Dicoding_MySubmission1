package com.roziqrizal.mysubmission.helper

import android.database.Cursor
import com.roziqrizal.mysubmission.db.DatabaseContract
import com.roziqrizal.mysubmission.entity.Note

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        val notesList = ArrayList<Note>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns.ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOGIN))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.REPOSITORY))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.IMAGE))
                notesList.add(Note(id, title, description, image))
            }
        }
        return notesList
    }
}