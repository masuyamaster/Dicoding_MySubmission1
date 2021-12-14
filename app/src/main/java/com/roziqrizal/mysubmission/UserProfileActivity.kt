package com.roziqrizal.mysubmission

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roziqrizal.mysubmission.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var activityViewModel: ModelUserActivity


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
        activityViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ModelUserActivity::class.java)

        showLoading(true)

        if(activityViewModel.usernameTV == ""){
            activityViewModel.usernameTV = person.username
        }
        activityViewModel.getDetailUser(activityViewModel.usernameTV)
        activityViewModel.isLoadingDetail.observe(this, {
            if(!it){
                tvUsername.text = activityViewModel.userGithub
                tvFullName.text = activityViewModel.usernameTV
                tvCompany.text = activityViewModel.company
                tvLocation.text = activityViewModel.location
                tvFollowing.text = activityViewModel.followingCount
                tvQtyRepo.text = activityViewModel.repository
                Glide.with(this)
                    .load(activityViewModel.avatar) // URL Gambar
                    .circleCrop() // Mengubah image menjadi lingkaran
                    .into(imgAvatar) // imageView mana yang akan diterapkan

            }
            showLoading(it)
        })
        activityViewModel.getFollower(activityViewModel.usernameTV)
        activityViewModel.getFollowing(activityViewModel.usernameTV)
        activityViewModel.isLoadingFollower.observe(this, {
            if(!it){
                listFollower = activityViewModel.listFollower
                val sectionsPagerAdapter = SectionsPagerAdapter(this)
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.TabLayout)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
                tvFollowers.text = activityViewModel.followerCount

            }
            showLoading(it)
        })
        activityViewModel.isLoadingFollowing.observe(this, {
            if(!it){
                listFollowing = activityViewModel.listFollowing
                val sectionsPagerAdapter = SectionsPagerAdapter(this)
                val viewPager: ViewPager2 = findViewById(R.id.view_pager)
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = findViewById(R.id.TabLayout)
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
                tvFollowing.text = activityViewModel.followingCount

            }
            showLoading(it)
        })


        supportActionBar?.elevation = 0f



    }

    companion object {
        var user_data = "userdata"
        var listFollower = ArrayList<ResponseFollowerItem>()
        var listFollowing = ArrayList<ResponseFollowerItem>()
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}