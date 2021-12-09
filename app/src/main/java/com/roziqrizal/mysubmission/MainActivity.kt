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
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: ModelMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.github_user_bar_title)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = LinearLayoutManager(this)



        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ModelMainActivity::class.java)

        showRecyclerList(mainViewModel.list)


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
                showLoading(true)
                mainViewModel.findUser(query)
                mainViewModel.isLoading.observe(this@MainActivity, {
                    showLoading(it)
                    if(!it){
                        showRecyclerList(mainViewModel.list);
                    }

                })
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

    fun showRecyclerList(listArr: ArrayList<UserItem>){
        val listHeroAdapter = ListUserAdapter(listArr)
        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                showSelectedHero(data)
            }
        })
        rvUser.adapter = listHeroAdapter
    }


    private fun showSelectedHero(user: UserItem) {
        val user = User(
            user.id,
            user.login,
            user.avatarUrl,
            user.login,
            user.login,
            user.login,
            user.login,
            user.login
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


    /*private fun getDataUser(username: String) {



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
                                val responseBody = response.body()
                                if (responseBody != null) {

                                    if(responseBody.company!=null){
                                        dataCompany = responseBody.company
                                    }
                                    if (responseBody.location!=null){
                                        dataLocation = responseBody.location
                                    }


                                    //showRecyclerList()
                                    val listHero = ArrayList<User>()

                                    val hero = User(dataName,dataUser, dataPhoto, dataCompany, dataLocation, "", "", "")
                                    listHero.add(hero)

                                    list.addAll(listHero)
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
    }*/
}