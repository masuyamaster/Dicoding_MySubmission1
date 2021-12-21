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

class ModelFollowerFragment: ViewModel()  {

    var listFollower = ArrayList<ResponseFollowerItem>()

    private val _follower = MutableLiveData<ArrayList<ResponseFollowerItem>>()

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    companion object{
        private const val TAG = "FollowerActivityModel"
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
                    Log.e(ModelFollowerFragment.TAG, "onFailure: ${response.message()}")
                }
                _isLoadingFollower.value = false
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowerItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                _follower.value = null
                println(t.message.toString())
                Log.e(ModelFollowerFragment.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}