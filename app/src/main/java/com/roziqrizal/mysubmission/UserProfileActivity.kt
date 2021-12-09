package com.roziqrizal.mysubmission

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roziqrizal.mysubmission.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.github_user_detail_bar_title)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgAvatar: ImageView = findViewById(R.id.iv_avatar)
        val tvUsername: TextView = findViewById(R.id.username)
        val tvFullName: TextView = findViewById(R.id.fullname)
        val tvCompany: TextView = findViewById(R.id.company)
        val tvLocation: TextView = findViewById(R.id.location)
        val tvFollowers: TextView = findViewById(R.id.followers)
        val tvFollowing: TextView = findViewById(R.id.following)
        val tvQtyRepo: TextView = findViewById(R.id.tv_qty_repo)

        val person = intent.getParcelableExtra<User>(user_data) as User

        Glide.with(this)
            .load(person.avatar) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(imgAvatar) // imageView mana yang akan diterapkan

        tvUsername.text = person.user_github.toString()
        tvFullName.text = person.username
        tvCompany.text = person.company
        tvLocation.text = person.location
        tvFollowers.text = person.follower + " Followers"
        tvFollowing.text = person.following + " Followings"
        tvQtyRepo.text = person.repository

        println("================ "+person.username)
        FollowerFragment.newInstance(person.username,person.company)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.TabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    companion object {
        const val user_data = "userdata"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}