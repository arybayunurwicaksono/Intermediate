package com.dguitarclassic.intermediate

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dguitarclassic.intermediate.Api.Injection
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.Repository.StoryRepository
import com.dguitarclassic.intermediate.Response.ListStoryItem

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    val getAllStory: LiveData<PagingData<ListStoryItem>> = storyRepository.getStory().cachedIn(viewModelScope)
}
class StoryViewModelFactory(private val context: Context, private val authPref: AuthPref) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository(context,authPref)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}