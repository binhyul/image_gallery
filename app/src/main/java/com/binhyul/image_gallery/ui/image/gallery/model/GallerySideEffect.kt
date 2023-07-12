package com.binhyul.image_gallery.ui.image.gallery.model

import com.binhyul.image_gallery.domain.model.ImageModel

sealed class GallerySideEffect {
    class MoveDetailPage(val model: ImageModel) : GallerySideEffect()
}