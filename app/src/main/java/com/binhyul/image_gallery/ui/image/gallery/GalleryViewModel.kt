package com.binhyul.image_gallery.ui.image.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.domain.usecase.LoadPagingImageUseCase
import com.binhyul.image_gallery.ui.image.gallery.model.GallerySideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val loadPagingImageUseCase: LoadPagingImageUseCase,
) : ViewModel() {

    private val _sideEffect: MutableSharedFlow<GallerySideEffect> =
        MutableSharedFlow(extraBufferCapacity = 1)
    val sideEffect: SharedFlow<GallerySideEffect> = _sideEffect.asSharedFlow()

    private val _loading: MutableSharedFlow<Boolean> =
        MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading


    private var currentImages: Flow<PagingData<ImageModel>>? = null

    suspend fun loadImage(): Flow<PagingData<ImageModel>> {
        val lastResult = currentImages
        if (lastResult != null) {
            return lastResult
        }
        val newResult = loadPagingImageUseCase().cachedIn(viewModelScope)
        currentImages = newResult
        return newResult
    }

    init {
        loading()
    }

    fun moveDetailPage(imageModel: ImageModel) =
        updateSideEffect(GallerySideEffect.MoveDetailPage(imageModel))

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