package com.roziqrizal.mysubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.roziqrizal.mysubmission.databinding.ActivityFavouriteBinding
import com.roziqrizal.mysubmission.databinding.ActivityMainBinding
import com.roziqrizal.mysubmission.db.UserHelper
import com.roziqrizal.mysubmission.entity.Note
import com.roziqrizal.mysubmission.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var adapter: UserFavouriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.github_user_favourite_bar_title)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserFavouriteAdapter(object : UserFavouriteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                println("trace 1")
                loadUserProfile(selectedNote)
            }
        })



        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listUserFavourite = list
            }
        }

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter

    }

    private fun loadUserProfile(selectedNote: Note?) {
        lifecycleScope.launch {
            val noteHelper = UserHelper.getInstance(applicationContext)
            var isFavourite :String = "not favourite"
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryById(selectedNote?.title.toString())
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                isFavourite = "favourite"
            }
            noteHelper.close()

            val user = User(
                0,
                selectedNote?.title.toString(),
                selectedNote?.image.toString(),
                selectedNote?.description.toString(),
                isFavourite,
                selectedNote?.title.toString(),
                selectedNote?.title.toString(),
                selectedNote?.title.toString()
            )

            val moveWithObjectIntent = Intent(this@FavouriteActivity, UserProfileActivity::class.java)
            moveWithObjectIntent.putExtra(UserProfileActivity.user_data, user)
            startActivity(moveWithObjectIntent)
        }
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
            println("trace 1")
            val noteHelper = UserHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                println("trace 2")
                adapter.listUserFavourite = notes
                adapter.notifyDataSetChanged()
            } else {
                adapter.listUserFavourite = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }
}