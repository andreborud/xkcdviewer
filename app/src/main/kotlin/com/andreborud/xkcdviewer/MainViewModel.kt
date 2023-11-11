package com.andreborud.xkcdviewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreborud.domain.GetLatestComicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {

    @Inject
    lateinit var getLatestComicUseCase: GetLatestComicUseCase

    fun testGetComic() {
        viewModelScope.launch {
            val comic = getLatestComicUseCase.invoke()
        }
    }
}