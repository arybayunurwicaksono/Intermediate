package com.dguitarclassic.intermediate.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dguitarclassic.intermediate.Response.CreateStoryResponse
import com.dguitarclassic.intermediate.Api.ApiConfig
import com.dguitarclassic.intermediate.Preferences.AuthPref
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateViewModel (private val pref: AuthPref): ViewModel() {

    private val _uploadResponse = MutableLiveData<CreateStoryResponse>()
    val uploadResponse: LiveData<CreateStoryResponse> = _uploadResponse

    fun uploadStory(token:String, file: MultipartBody.Part, description: RequestBody){
        val client = ApiConfig.getApiService().postStories(token, file,description)

        client.enqueue(object : Callback<CreateStoryResponse> {
            override fun onResponse(
                call: Call<CreateStoryResponse>,
                response: Response<CreateStoryResponse>
            ) {
                if (response.isSuccessful){
                    _uploadResponse.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<CreateStoryResponse>, t: Throwable) {
                Log.d("upload error", t.message.toString())
            }

        }
        )
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

}