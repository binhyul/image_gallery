package com.binhyul.image_gallery.ui.image.horizontal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binhyul.image_gallery.data.remote.source.ImageRemotePagingSource
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.domain.usecase.LoadImageUseCase
import com.binhyul.image_gallery.ui.image.gallery.model.GallerySideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


@HiltViewModel
class HorizontalGalleryViewModel @Inject constructor(
    private val loadImageUseCase: LoadImageUseCase,
) : ViewModel() {

    private var start = 0
    private val limit = ImageRemotePagingSource.PAGE_SIZE
    private var endPage = false
    private var loadImageMutex = Mutex()

    private val _sideEffect: MutableSharedFlow<GallerySideEffect> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val sideEffect: SharedFlow<GallerySideEffect> = _sideEffect.asSharedFlow()

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading

    private val _images: MutableStateFlow<List<ImageModel>> = MutableStateFlow(emptyList())
    val images: StateFlow<List<ImageModel>> = _images

    init {
        loading()
        loadImage()
    }

    fun moveDetailPage(imageModel: ImageModel) =
        updateSideEffect(GallerySideEffect.MoveDetailPage(imageModel))

    fun loadImage() {
        if (endPage || loadImageMutex.isLocked) return
        loading()
        viewModelScope.launch {
            loadImageMutex.withLock {
                try {
                    val list: List<ImageModel> = loadImageUseCase(start, limit)
                    if (list.isEmpty() || list.size < limit) {
                        endPage = true
                        return@launch
                    }
                    start += ImageRemotePagingSource.PAGE_SIZE
                    _images.value += list
                    delay(3000L)
                } finally {
                    loaded()
                }
            }
        }
    }


    private fun updateSideEffect(sideEffect: GallerySideEffect) = viewModelScope.launch {
        _sideEffect.emit(sideEffect)
    }

    private fun loading() {
        viewModelScope.launch {
            _loading.emit(true)
        }
    }

    fun loaded() {
        viewModelScope.launch {
            _loading.emit(false)
        }
    }
}