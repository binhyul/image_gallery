package com.binhyul.image_gallery.ui.image.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binhyul.image_gallery.domain.model.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _imageDetail: MutableStateFlow<ImageModel> =
        MutableStateFlow(ImageModel())
    val imageDetail: StateFlow<ImageModel> = _imageDetail

    init {
        viewModelScope.launch {
            val model = savedStateHandle.get<ImageModel>(DETAIL_MODEL)
            _imageDetail.value = model ?: ImageModel()
        }
    }

    companion object {
        const val DETAIL_MODEL = "model"
    }

}