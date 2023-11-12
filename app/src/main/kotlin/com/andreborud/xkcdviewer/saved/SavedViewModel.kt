package com.andreborud.xkcdviewer.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreborud.domain.local.ComicLocalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var comicLocalUseCases: ComicLocalUseCases

    private val _state = MutableSharedFlow<SavedState>(1)
    val state: SharedFlow<SavedState>
        get() = _state

    fun onUser(intent: SavedIntent) {
        when (intent) {
            is SavedIntent.Refresh -> {
                viewModelScope.launch {
                    refreshList()
                }
            }
            is SavedIntent.Remove -> {
                viewModelScope.launch {
                    comicLocalUseCases.saveOrDelete(intent.comic)
                    refreshList()
                }
            }
        }
    }

    private suspend fun refreshList() {
        val comics = comicLocalUseCases.getAll()
        _state.emit(SavedState.OnComicsLoaded(comics))
    }
}