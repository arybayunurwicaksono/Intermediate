package com.dguitarclassic.intermediate.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dguitarclassic.intermediate.Api.ApiConfig
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.Response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: AuthPref) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun saveUser(prefModel: User){
        viewModelScope.launch {
            pref.saveUser(prefModel)
        }
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun LoginAccounts( email: String, password: String){
        val client = ApiConfig.getApiService().loginAccount(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    _loginResponse.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("error login", t.message.toString())
            }
        })
    }
}