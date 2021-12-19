package com.roziqrizal.mysubmission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roziqrizal.mysubmission.viewmodel.ModelMainActivity
import com.roziqrizal.mysubmission.viewmodel.ModelSettingActivity
import com.roziqrizal.mysubmission.viewmodel.ModelUserActivity

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModelSettingActivity::class.java)) {
            return ModelSettingActivity(pref) as T
        } else if(modelClass.isAssignableFrom(ModelMainActivity::class.java)){
            return ModelMainActivity(pref) as T
        } else if(modelClass.isAssignableFrom(ModelUserActivity::class.java)){
            return ModelUserActivity(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}