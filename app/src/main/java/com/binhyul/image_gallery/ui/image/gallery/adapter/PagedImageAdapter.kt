package com.binhyul.image_gallery.ui.image.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.binhyul.component.databinding.ViewImageCardBinding
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.ui.image.gallery.ImageCardController

class PagedImageAdapter(
    private val imageCardController: ImageCardController
) : PagingDataAdapter<ImageModel, ImageCardViewHolder>(
    object : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(
            oldItem: ImageModel,
            newItem: ImageModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ImageModel,
            newItem: ImageModel
        ): Boolean {
            return oldItem.url == newItem.url
        }
    }
) {

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        return ImageCardViewHolder(
            ViewImageCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), imageCardController
        )
    }

}

