package com.roziqrizal.mysubmission.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.roziqrizal.mysubmission.ApiConfig
import com.roziqrizal.mysubmission.ResponseSearch
import com.roziqrizal.mysubmission.SettingPreferences
import com.roziqrizal.mysubmission.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMainActivity(private val pref: SettingPreferences): ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

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
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }





}