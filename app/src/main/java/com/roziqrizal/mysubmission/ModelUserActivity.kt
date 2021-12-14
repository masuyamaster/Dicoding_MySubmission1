package com.roziqrizal.mysubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelUserActivity: ViewModel() {
    var listFollower = ArrayList<ResponseFollowerItem>()
    var listFollowing = ArrayList<ResponseFollowerItem>()
    var userGithub: String = ""
    var usernameTV: String = ""
    var company: String = ""
    var location: String = ""
    var followerCount: String = ""
    var followingCount: String = ""
    var repository: String = ""
    var avatar: String = ""

    private val _follower = MutableLiveData<ArrayList<ResponseFollowerItem>>()
    private val _following = MutableLiveData<ArrayList<ResponseFollowerItem>>()

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    companion object{
        private const val TAG = "UserActivityModel"
    }

    fun getFollower(username: String){
        _isLoadingFollower.value = true
        val client = ApiConfig.getApiService().getUsersDetailFollower(username)
        client.enqueue(object : Callback<ArrayList<ResponseFollowerItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseFollowerItem>>, response: Response<ArrayList<ResponseFollowerItem>>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    _follower.value = responseBody
                    listFollower = responseBody!!
                } else {
                    Log.e(ModelUserActivity.TAG, "onFailure: ${response.message()}")
                }
                _isLoadingFollower.value = false
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowerItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                _follower.value = null
                println(t.message.toString())
                Log.e(ModelUserActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String){
        _isLoadingFollowing.value = true
        val clientFollowing = ApiConfig.getApiService().getUsersDetailFollowing(username)
        clientFollowing.enqueue(object : Callback<ArrayList<ResponseFollowerItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseFollowerItem>>, response: Response<ArrayList<ResponseFollowerItem>>) {
                val responseBodyFollowing = response.body()
                if (response.isSuccessful) {
                    _following.value = responseBodyFollowing
                    listFollowing = responseBodyFollowing!!
                } else {
                    Log.e(ModelUserActivity.TAG, "onFailure: ${response.message()}")
                }
                _isLoadingFollowing.value = false
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowerItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                _following.value = null
                println(t.message.toString())
                Log.e(ModelUserActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUser(username: String){
        _isLoadingDetail.value = true
        val usersDetail = ApiConfig.getApiService().getGithubUsersDetail(username)
        usersDetail.enqueue(object : Callback<ResponseGithubUsersDetail> {
            override fun onResponse(call: Call<ResponseGithubUsersDetail>, response: Response<ResponseGithubUsersDetail>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    usernameTV = responseBody.login
                    userGithub = responseBody.name
                    if (responseBody.company!=null)
                    company = responseBody.company
                    if (responseBody.location!=null)
                    location = responseBody.location
                    if (responseBody.reposUrl!=null)
                    repository = responseBody.reposUrl
                    if (responseBody.avatarUrl!=null)
                    avatar = responseBody.avatarUrl
                    if (responseBody.following!=null) {
                        followingCount = "${responseBody.following} Following"
                    }
                    if (responseBody.followers!=null) {
                        followerCount = "${responseBody.followers} Follower"
                    }

                }
                _isLoadingDetail.value = false
            }
            override fun onFailure(call: Call<ResponseGithubUsersDetail>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoadingDetail.value = false
            }}
        )
    }
}