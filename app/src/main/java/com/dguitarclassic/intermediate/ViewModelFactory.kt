package com.dguitarclassic.intermediate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dguitarclassic.intermediate.Model.CreateViewModel
import com.dguitarclassic.intermediate.Model.MainViewModel
import com.dguitarclassic.intermediate.Model.LoginViewModel
import com.dguitarclassic.intermediate.Preferences.AuthPref

class ViewModelFactory(private val pref: AuthPref) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java)->{
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CreateViewModel::class.java)->{
                CreateViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}