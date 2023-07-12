package com.binhyul.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.binhyul.component.databinding.ImageCardBinding

class ImageCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: ImageCardBinding =
        ImageCardBinding.inflate(LayoutInflater.from(context), this)

    init {
        foreground = AppCompatResources.getDrawable(context, R.drawable.bg_selectable_item)
    }

    fun setState(
        thumbnail: String,
        onClickAction: () -> Unit,
    ) {

    }
}