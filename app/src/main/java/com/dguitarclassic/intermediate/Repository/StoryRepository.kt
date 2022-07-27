package com.dguitarclassic.intermediate.Repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dguitarclassic.intermediate.Api.ApiService
import com.dguitarclassic.intermediate.Response.ListStoryItem
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.Paging.Paging

class StoryRepository(private val apiService: ApiService, private val authPref: AuthPref) {

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                Paging(apiService, authPref)
            }
        ).liveData
    }
}