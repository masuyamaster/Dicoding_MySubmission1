package com.roziqrizal.mysubmission

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }

        list.addAll(listHeroes)
        showRecyclerList()
    }

    private val listHeroes: ArrayList<User>
        @SuppressLint("Recycle")
        get() {
            val dataUser = resources.getStringArray(R.array.user_github)
            val dataName = resources.getStringArray(R.array.username)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollower = resources.getStringArray(R.array.follower)
            val dataFollowing = resources.getStringArray(R.array.following)

            val listHero = ArrayList<User>()
            for (i in dataName.indices) {
                val hero = User(dataUser[i],dataName[i], dataPhoto.getResourceId(i, -1), dataCompany[i].replace("\"",""), dataLocation[i], dataRepository[i], dataFollower[i], dataFollowing[i])
                listHero.add(hero)
            }
            return listHero
        }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListUserAdapter(list)
        rvUser.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(user: User) {
        val user = User(
            user.user_github,
            user.username,
            user.avatar,
            user.company,
            user.location,
            user.repository,
            user.follower,
            user.following
        )

        val moveWithObjectIntent = Intent(this@MainActivity, UserProfileActivity::class.java)
        moveWithObjectIntent.putExtra(UserProfileActivity.user_data, user)
        startActivity(moveWithObjectIntent)
    }
}