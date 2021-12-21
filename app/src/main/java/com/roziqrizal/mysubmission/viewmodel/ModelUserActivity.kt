package com.roziqrizal.mysubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.roziqrizal.mysubmission.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelUserActivity(private val pref: SettingPreferences): ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }




    var listFollower = ArrayList<ResponseFollowerItem>()

    var isFavourite = ""
    var userGithub: String = ""
    var usernameTV: String = ""
    var company: String = ""
    var location: String = ""
    var followerCount: String = ""
    var followingCount: String = ""
    var repository: String = ""
    var avatar: String = ""

    private val _follower = MutableLiveData<ArrayList<ResponseFollowerItem>>()


    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower



    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    companion object{
        private const val TAG = "UserActivityModel"
        var user_data = "userdata"
    }



    fun getDetailUser(username: String){
        _isLoadingDetail.value = true
        val usersDetail = ApiConfig.getApiService().getGithubUsersDetail(username)
        usersDetail.enqueue(object : Callback<ResponseGithubUsersDetail> {
            override fun onResponse(call: Call<ResponseGithubUsersDetail>, response: Response<ResponseGithubUsersDetail>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    usernameTV = responseBody.login
                    if (responseBody.name!=null)
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