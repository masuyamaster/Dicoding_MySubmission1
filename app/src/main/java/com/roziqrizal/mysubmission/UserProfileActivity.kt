package com.roziqrizal.mysubmission

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roziqrizal.mysubmission.databinding.ActivityUserProfileBinding
import com.roziqrizal.mysubmission.db.DatabaseContract
import com.roziqrizal.mysubmission.db.UserHelper
import com.roziqrizal.mysubmission.entity.Note
import com.roziqrizal.mysubmission.viewmodel.ModelSettingActivity
import com.roziqrizal.mysubmission.viewmodel.ModelUserActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var activityViewModel: ModelUserActivity

    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var userHelper: UserHelper
    private var title: String = ""
    private var repository: String = ""
    private var image: String = ""





    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.github_user_detail_bar_title)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoadingDetail(true)


        val imgAvatar: ImageView = findViewById(R.id.iv_avatar)
        val tvUsername: TextView = findViewById(R.id.username)
        val tvFullName: TextView = findViewById(R.id.fullname)
        val tvCompany: TextView = findViewById(R.id.company)
        val tvLocation: TextView = findViewById(R.id.location)
        val tvFollowers: TextView = findViewById(R.id.followers)
        val tvFollowing: TextView = findViewById(R.id.following)
        val tvQtyRepo: TextView = findViewById(R.id.tv_qty_repo)





        val pref = SettingPreferences.getInstance(dataStore)
        activityViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ModelUserActivity::class.java
        )

        activityViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })



        if(activityViewModel.usernameTV == ""){
            activityViewModel.usernameTV = (intent.getParcelableExtra<User>(user_data) as User).username
        }
        if(activityViewModel.isFavourite == ""){
            activityViewModel.isFavourite = (intent.getParcelableExtra<User>(user_data) as User).location
        }

        activityViewModel.getDetailUser(activityViewModel.usernameTV)
        user_data = activityViewModel.usernameTV
        activityViewModel.isLoadingDetail.observe(this, {
            if(!it){



                tvUsername.text = activityViewModel.userGithub
                tvFullName.text = activityViewModel.usernameTV
                title = activityViewModel.usernameTV
                tvCompany.text = activityViewModel.company
                tvLocation.text = activityViewModel.location
                tvFollowing.text = activityViewModel.followingCount
                tvFollowers.text = activityViewModel.followerCount
                tvQtyRepo.text = activityViewModel.repository
                repository = activityViewModel.repository
                Glide.with(this)
                    .load(activityViewModel.avatar) // URL Gambar
                    .circleCrop() // Mengubah image menjadi lingkaran
                    .into(imgAvatar) // imageView mana yang akan diterapkan
                image = activityViewModel.avatar



            }
            showLoadingDetail(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.TabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()



        /*activityViewModel.isLoadingFollowing.observe(this, {
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
        })*/
        supportActionBar?.elevation = 0f


        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()


        /*if (result > 0) {
        } else {
        }*/

    }

    override fun onBackPressed() {
        val moveWithObjectIntent = Intent(this@UserProfileActivity, MainActivity::class.java)
        startActivity(moveWithObjectIntent)
        super.onBackPressed()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favourite_menu, menu)

        if (activityViewModel.isFavourite=="favourite") {
            menu.getItem(0).icon = resources.getDrawable(R.drawable.ic_baseline_star_yellow_24)
        } else {
            menu.getItem(0).icon = resources.getDrawable(R.drawable.ic_baseline_star_24)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.favourite -> {
                if (activityViewModel.isFavourite=="favourite") {
                    item.setIcon(R.drawable.ic_baseline_star_24)
                    delete(title)
                    activityViewModel.isFavourite = "not favourite"

                } else {
                    item.setIcon(R.drawable.ic_baseline_star_yellow_24)
                    save()
                    activityViewModel.isFavourite = "favourite"

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"

    }

    private fun showLoadingDetail(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    private fun save() {
        val values = ContentValues()
        values.put(DatabaseContract.NoteColumns.LOGIN, title)
        values.put(DatabaseContract.NoteColumns.REPOSITORY, repository)
        values.put(DatabaseContract.NoteColumns.IMAGE, image)

        val result = userHelper.insert(values)
        if (result > 0) {
            Toast.makeText(this@UserProfileActivity, "User ditambahkan ke favourite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@UserProfileActivity, "gagal ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun delete(title: String){
        val result = userHelper.deleteById(title).toLong()
        if (result > 0) {
            Toast.makeText(this@UserProfileActivity, "User dihapus dari favourite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@UserProfileActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
        }
    }
}