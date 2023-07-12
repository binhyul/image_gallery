package com.binhyul.image_gallery.ui.image.gallery

import com.binhyul.image_gallery.domain.model.ImageModel

interface ImageCardController {
    fun onClickImage(image: ImageModel)
}