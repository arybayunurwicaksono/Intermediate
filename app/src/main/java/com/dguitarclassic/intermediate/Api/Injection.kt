package com.dguitarclassic.intermediate.Api

import android.content.Context
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.Repository.StoryRepository

object Injection {
    fun provideRepository(context: Context, authPref: AuthPref) : StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, authPref)
    }
}