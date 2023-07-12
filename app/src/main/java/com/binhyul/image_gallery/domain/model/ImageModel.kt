package com.binhyul.image_gallery.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val albumId: Int = 0,
    val id: Int = 0,
    val url: String = "",
    val title: String = "",
    val thumbnailUrl: String = ""
) : Parcelable