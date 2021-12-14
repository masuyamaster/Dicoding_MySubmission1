package com.roziqrizal.mysubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserFollower(private val listUser: ArrayList<ResponseFollowerItem>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>()  {

    private lateinit var onItemClickCallback: ListUserAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_item_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_user_name)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
        var tvLocation: TextView = itemView.findViewById(R.id.tv_item_location)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListUserAdapter.ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.imgAvatar) // imageView mana yang akan diterapkan
        holder.tvUsername.text = listUser[position].login
        holder.tvCompany.text = listUser[position].id.toString()
        holder.tvLocation.text = listUser[position].reposUrl


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseFollowerItem)
    }

    override fun getItemCount(): Int = listUser.size
}