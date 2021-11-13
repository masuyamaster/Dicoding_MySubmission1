package com.roziqrizal.mysubmission

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class UserProfileActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val imgAvatar: ImageView = findViewById(R.id.iv_avatar)
        val tvUsername: TextView = findViewById(R.id.username)
        val tvFullName: TextView = findViewById(R.id.fullname)
        val tvCompany: TextView = findViewById(R.id.company)
        val tvLocation: TextView = findViewById(R.id.location)
        val tvFollowers: TextView = findViewById(R.id.followers)
        val tvFollowing: TextView = findViewById(R.id.following)
        val tvQtyRepo: TextView = findViewById(R.id.tv_qty_repo)

        val person = intent.getParcelableExtra<User>(user_data) as User

        imgAvatar.setImageResource(person.avatar)
        Glide.with(this)
            .load(imgAvatar.drawable) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(imgAvatar) // imageView mana yang akan diterapkan

        tvUsername.text = person.user_github
        tvFullName.text = person.username
        tvCompany.text = person.company
        tvLocation.text = person.location
        tvFollowers.text = person.follower + " Followers"
        tvFollowing.text = person.following + " Followings"
        tvQtyRepo.text = person.repository

    }

    companion object {
        const val user_data = "userdata"
    }
}