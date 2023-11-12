package com.andreborud.xkcdviewer.comics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreborud.common.IsFirstComicExceptions
import com.andreborud.common.IsLatestComicExceptions
import com.andreborud.common.XkcdComic
import com.andreborud.domain.local.ComicLocalUseCases
import com.andreborud.domain.remote.GetLatestComicRemoteUseCase
import com.andreborud.domain.remote.GetSpecificComicRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {

    @Inject
    lateinit var getLatestComicUseCase: GetLatestComicRemoteUseCase

    @Inject
    lateinit var getSpecificComicRemoteUseCase: GetSpecificComicRemoteUseCase

    @Inject
    lateinit var comicLocalUseCases: ComicLocalUseCases

    private val _state = MutableSharedFlow<ComicsState>(1)
    val state: SharedFlow<ComicsState>
        get() = _state

    private var currentComic: XkcdComic? = null
    private var latestComicNumber = -1

    init {
        viewModelScope.launch {
            currentComic = getLatestComicUseCase.invoke()
            currentComic?.let {
                latestComicNumber = it.num
                _state.emit(ComicsState.OnComicDownloaded(
                    comic = it,
                    isFirst = it.isFirstComic(),
                    isLatest = it.isLatestComic(),
                    isSaved = false
                ))
            }
        }
    }

    fun onUser(intent: ComicsIntent) {
        when (intent) {
            ComicsIntent.DownloadNext -> {
                currentComic?.let {
                    try {
                        downloadSpecificComic(it.getNextComicNumber(latestComicNumber))
                    } catch (e: IsLatestComicExceptions) {
                        viewModelScope.launch { _state.emit(ComicsState.OnError(message = "You are already on the latest issue")) }
                    }
                }
            }
            ComicsIntent.DownloadPrevious -> {
                currentComic?.let {
                    try {
                        downloadSpecificComic(it.getPreviousComicNumber())
                    } catch (e: IsFirstComicExceptions) {
                        viewModelScope.launch { _state.emit(ComicsState.OnError(message = "That's it, this is the first one!")) }
                    }
                }
            }
            ComicsIntent.Save -> {
                currentComic?.let {
                    viewModelScope.launch {
                        when (comicLocalUseCases.saveOrDelete(it)) {
                            ComicLocalUseCases.OperationResult.SAVED -> {
                                viewModelScope.launch { _state.emit(ComicsState.OnSaved) }
                            }
                            ComicLocalUseCases.OperationResult.DELETED -> {
                                viewModelScope.launch { _state.emit(ComicsState.OnUnsaved) }
                            }
                        }
                    }
                }
            }
            ComicsIntent.Share -> {
                viewModelScope.launch { _state.emit(ComicsState.OnShare(link = "https://xkcd.com/${currentComic?.num ?: 1}/")) }
            }
        }
    }

    private fun downloadSpecificComic(number: Int) {
        viewModelScope.launch {
            currentComic = getSpecificComicRemoteUseCase.invoke(number)
            currentComic?.let {
                _state.emit(ComicsState.OnComicDownloaded(
                    comic = it,
                    isFirst = it.isFirstComic(),
                    isLatest = it.isLatestComic(),
                    isSaved = false
                ))
            }
        }
    }

    private fun XkcdComic.isFirstComic() = num == 2
    private fun XkcdComic.isLatestComic() = num == latestComicNumber
}