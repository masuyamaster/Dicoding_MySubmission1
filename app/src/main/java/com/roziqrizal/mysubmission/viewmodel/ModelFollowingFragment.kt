package com.roziqrizal.mysubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roziqrizal.mysubmission.ApiConfig
import com.roziqrizal.mysubmission.ResponseFollowerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelFollowingFragment: ViewModel() {

    var listFollowing = ArrayList<ResponseFollowerItem>()
    private val _following = MutableLiveData<ArrayList<ResponseFollowerItem>>()

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    companion object{
        private const val TAG = "FollowingActivityModel"
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
                    Log.e(ModelFollowingFragment.TAG, "onFailure: ${response.message()}")
                }
                _isLoadingFollowing.value = false
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowerItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                _following.value = null
                println(t.message.toString())
                Log.e(ModelFollowingFragment.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}