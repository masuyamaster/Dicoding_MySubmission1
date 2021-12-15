package com.roziqrizal.mysubmission

import android.util.Log
import androidx.lifecycle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMainActivity: ViewModel() {

    var list = ArrayList<UserItem>()

    private val _search = MutableLiveData<ArrayList<UserItem>>()
    //val search: LiveData<ArrayList<UserItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        private const val TAG = "MainViewModel"
    }

    fun findUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUsers(username)

        client.enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(call: Call<ResponseSearch>, response: Response<ResponseSearch>) {
                val responseBody = response.body()
                if (response.isSuccessful) {

                    println("============= "+responseBody?.items)
                    _search.value = responseBody?.items
                    list = responseBody!!.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                _isLoading.value = false
                _search.value = null
                Log.e(ModelMainActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }





}