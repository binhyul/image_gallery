package com.binhyul.image_gallery.ui.image.gallery.adapter

import androidx.recyclerview.widget.RecyclerView
import com.binhyul.component.databinding.ViewImageCardBinding
import com.binhyul.component.loadUrlImage
import com.binhyul.component.onThrottleClick
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.ui.image.gallery.ImageCardController


class ImageCardViewHolder(
    private val bindingItemView: ViewImageCardBinding,
    private val imageCardController: ImageCardController
) : RecyclerView.ViewHolder(
    bindingItemView.root
) {

    fun bind(model: ImageModel) {
        with(model) {
            bindingItemView.root.binding.ivImg.loadUrlImage(model.thumbnailUrl)
            bindingItemView.root.binding.ivImg.onThrottleClick {
                imageCardController.onClickImage(model)
            }
        }
    }
}

