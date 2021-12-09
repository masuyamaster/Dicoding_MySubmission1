package com.roziqrizal.mysubmission

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roziqrizal.mysubmission.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.github_user_bar_title)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataUser(query)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                //getDataUser(newText)
                return false
            }
        })
        return true
    }


    private fun getDataUser(username: String) {

        showLoading(true)

        val client = ApiConfig.getApiService().getGithubUsers(username)

        client.enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(call: Call<ResponseSearch>, response: Response<ResponseSearch>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val item: List<ItemsItem?>? = responseBody.items
                    list.clear()
                    for (i in item?.indices!!) {
                        val dataUser: String = item[i]!!.login
                        val dataName: Int = item[i]!!.id
                        val dataPhoto: String = item[i]!!.avatarUrl
                        var dataCompany = ""
                        var dataLocation = ""

                        val usersDetail = ApiConfig.getApiService().getGithubUsersDetail(dataUser)
                        usersDetail.enqueue(object : Callback<ResponseGithubUsersDetail> {
                            override fun onResponse(call: Call<ResponseGithubUsersDetail>, response: Response<ResponseGithubUsersDetail>) {
                                println("company $response")
                                val responseBody = response.body()
                                if (responseBody != null) {

                                    if(responseBody.company!=null){
                                        dataCompany = responseBody.company
                                    }
                                    if (responseBody.location!=null){
                                        dataLocation = responseBody.location
                                    }

                                    println("company response "+responseBody.company)
                                    println("company response $dataCompany")

                                    showRecyclerList(dataName,dataUser, dataPhoto, dataCompany, dataLocation, "", "", "")
                                }
                            }
                            override fun onFailure(call: Call<ResponseGithubUsersDetail>, t: Throwable) {
                                Log.e(TAG, "onFailure: ${t.message.toString()}")
                            }}
                        )



                    }

                }
            }
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showRecyclerList(dataName: Int,dataUser: String, dataPhoto: String, dataCompany: String, dataLocation: String, dataRepository: String, dataFollower: String, dataFollowing: String){
        //delay(3000)
        rvUser.layoutManager = LinearLayoutManager(this)

        val listHero = ArrayList<User>()

        val hero = User(dataName,dataUser, dataPhoto, dataCompany, dataLocation, dataRepository, dataFollower, dataFollowing)
        listHero.add(hero)

        list.addAll(listHero)
        println(list.size)

        val listHeroAdapter = ListUserAdapter(list)
        rvUser.adapter = listHeroAdapter
        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedHero(data)
            }
        })
        showLoading(false)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}