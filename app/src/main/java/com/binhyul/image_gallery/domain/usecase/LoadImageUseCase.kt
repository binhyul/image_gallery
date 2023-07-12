package com.binhyul.image_gallery.domain.usecase

import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.domain.repository.AppRepository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(start: Int, limit: Int): List<ImageModel> =
        repository.getImages(start, limit)
}