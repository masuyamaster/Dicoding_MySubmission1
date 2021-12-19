package com.roziqrizal.mysubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roziqrizal.mysubmission.databinding.ItemNoteBinding
import com.roziqrizal.mysubmission.entity.Note

class UserFavouriteAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<UserFavouriteAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUserFavourite[position])
    }
    override fun getItemCount(): Int = this.listUserFavourite.size
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvItemTitle.text = note.title
            binding.tvItemDescription.text = note.description
            Glide.with(itemView.context)
                .load(note.image) // URL Gambar
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }
    }

    fun addItem(note: Note) {
        this.listUserFavourite.add(note)
        notifyItemInserted(this.listUserFavourite.size - 1)
    }
    fun updateItem(position: Int, note: Note) {
        this.listUserFavourite[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listUserFavourite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listUserFavourite.size)
    }

    var listUserFavourite = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listUserFavourite.clear()
            }
            this.listUserFavourite.addAll(listNotes)
        }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }


}