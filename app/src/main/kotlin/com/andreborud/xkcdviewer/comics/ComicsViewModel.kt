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
import com.andreborud.xkcdviewer.extensions.ImagePersistence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    @Inject
    lateinit var getLatestComicUseCase: GetLatestComicRemoteUseCase

    @Inject
    lateinit var getSpecificComicRemoteUseCase: GetSpecificComicRemoteUseCase

    @Inject
    lateinit var comicLocalUseCases: ComicLocalUseCases

    @Inject
    lateinit var imagePersistence: ImagePersistence

    private val _state = MutableSharedFlow<ComicsState>(0)
    val state: SharedFlow<ComicsState>
        get() = _state

    private var currentComic: XkcdComic? = null
    private var latestComicNumber = -1
    private val specificComicNumber: String? by lazy { savedStateHandle[ComicsFragment.COMIC_NUMBER] }
    private val savedComic: XkcdComic? by lazy { savedStateHandle[ComicsFragment.COMIC] }

    fun onUser(intent: ComicsIntent) {
        when (intent) {
            ComicsIntent.Resume -> {
                viewModelScope.launch {
                    // If ComicsFragment was not opened with a specific comic load the latest one and store the number
                    if (specificComicNumber == null && savedComic == null) {
                        try {
                            if (currentComic == null)
                                currentComic = getLatestComicUseCase.invoke()
                            currentComic?.let {
                                latestComicNumber = it.num
                                _state.emit(ComicsState.SaveLatest(it.num))
                                _state.emit(
                                    ComicsState.OnComicDownloaded(
                                        comic = it,
                                        isFirst = it.isFirstComic(),
                                        isLatest = it.isLatestComic(),
                                        isSaved = isSaved(it.num)
                                    )
                                )
                            }
                        } catch (e: Exception) {
                            viewModelScope.launch { _state.emit(ComicsState.OnError(message = "An error occurred")) }
                        }
                    } else if (specificComicNumber != null) { // If Coming from search we should load a specific comic
                        loadSpecificComic(specificComicNumber!!.toInt())
                    } else { // If Coming from save we should load that comic from db and image from local storage
                        val image = imagePersistence.loadComicImage(savedComic!!.num)
                        _state.emit(ComicsState.OnLoadSaved(comic = savedComic!!, image))
                    }
                }
            }

            ComicsIntent.DownloadNext -> {
                currentComic?.let {
                    try {
                        loadSpecificComic(it.getNextComicNumber(latestComicNumber))
                    } catch (e: IsLatestComicExceptions) {
                        viewModelScope.launch { _state.emit(ComicsState.OnError(message = "You are already on the latest issue")) }
                    }
                }
            }

            ComicsIntent.DownloadPrevious -> {
                currentComic?.let {
                    try {
                        loadSpecificComic(it.getPreviousComicNumber())
                    } catch (e: IsFirstComicExceptions) {
                        viewModelScope.launch { _state.emit(ComicsState.OnError(message = "That's it, this is the first one!")) }
                    }
                }
            }

            ComicsIntent.Save -> {
                // When user clicks save we need to remove it from the db, or save it if it wasn't already
                val comic = currentComic ?: savedComic
                comic?.let {
                    viewModelScope.launch {
                        when (comicLocalUseCases.saveOrDelete(it)) {
                            ComicLocalUseCases.OperationResult.SAVED -> {
                                viewModelScope.launch { _state.emit(ComicsState.OnSaved) }
                                imagePersistence.saveComicImage(it.img, it.num)
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

            ComicsIntent.ShowExplanation -> {
                viewModelScope.launch { _state.emit(ComicsState.OnShowExplanation(link = "https://www.explainxkcd.com/wiki/index.php?title=${currentComic?.num ?: 1}")) }
            }
        }
    }

    private fun loadSpecificComic(number: Int) {
        viewModelScope.launch {
            try {
                currentComic = getSpecificComicRemoteUseCase.invoke(number)
                currentComic?.let {
                    _state.emit(
                        ComicsState.OnComicDownloaded(
                            comic = it,
                            isFirst = it.isFirstComic(),
                            isLatest = it.isLatestComic(),
                            isSaved = isSaved(number)
                        )
                    )
                }
            } catch (e: Exception) {
                viewModelScope.launch { _state.emit(ComicsState.OnError(message = "An error occurred")) }
            }
        }
    }

    private suspend fun isSaved(number: Int) = comicLocalUseCases.get(number) != null

    private fun XkcdComic.isFirstComic() = num == 2
    private fun XkcdComic.isLatestComic() = num == latestComicNumber
}