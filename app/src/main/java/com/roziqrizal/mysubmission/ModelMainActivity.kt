package com.roziqrizal.mysubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMainActivity: ViewModel() {

    var list = ArrayList<User>()

    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findUser("adam")
    }

    private fun findUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUsers(username)

        client.enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(call: Call<ResponseSearch>, response: Response<ResponseSearch>) {
                val responseBody = response.body()
                _isLoading.value = false
                if (response.isSuccessful) {
                    //_search.value = response.body()
                    _search.value = responseBody?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                _search.value = null
                Log.e(ModelMainActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDataUser(username: String) {

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

                                    val listHero = ArrayList<User>()

                                    val hero = User(dataName,dataUser, dataPhoto, dataCompany, dataLocation, "", "", "")
                                    listHero.add(hero)
                                }
                            }
                            override fun onFailure(call: Call<ResponseGithubUsersDetail>, t: Throwable) {
                                Log.e(ModelMainActivity.TAG, "onFailure: ${t.message.toString()}")
                            }}
                        )

                    }

                }
            }
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                Log.e(ModelMainActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }



}