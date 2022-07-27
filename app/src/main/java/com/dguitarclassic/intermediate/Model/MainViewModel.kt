package com.dguitarclassic.intermediate.Model

import android.util.Log
import androidx.lifecycle.*
import com.dguitarclassic.intermediate.Api.ApiConfig
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.Response.StoriesResponseWithLocation
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: AuthPref) : ViewModel() {
    private val _list = MutableLiveData<StoriesResponseWithLocation>()
    val list: LiveData<StoriesResponseWithLocation> = _list

    fun getListStoriesWithLocation(token:String){
        val client = ApiConfig.getApiService().getAllStorieswithLocation(token)

        client.enqueue(object : Callback<StoriesResponseWithLocation> {
            override fun onResponse(
                call: Call<StoriesResponseWithLocation>,
                response: Response<StoriesResponseWithLocation>
            ) {
                if (response.isSuccessful){
                    _list.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<StoriesResponseWithLocation>, t: Throwable) {
                Log.d("error getlist", t.message.toString())
            }

        }
        )
    }


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }


}