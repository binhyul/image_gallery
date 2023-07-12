package com.binhyul.image_gallery.domain.usecase

import androidx.paging.PagingData
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadPagingImageUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(): Flow<PagingData<ImageModel>> =
        repository.getImages()
}