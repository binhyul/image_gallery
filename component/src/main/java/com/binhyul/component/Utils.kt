package com.binhyul.component

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


fun ImageView.loadUrlImage(
    url: String,
    @DrawableRes placeHolder: Int = R.drawable.place_holder,
    imageLoadedCallback: (() -> Unit)? = null,
    imageLoadFailCallback: (() -> Unit)? = imageLoadedCallback,
) {
    try {
        Glide.with(this.context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageLoadFailCallback?.invoke()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageLoadedCallback?.invoke()
                    return false
                }
            })
            .placeholder(placeHolder)
            .into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


class OnThrottleClickListener(
    private val onClickListener: View.OnClickListener,
    private val intervalMs: Long
) : View.OnClickListener {
    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({ clickable = true }, intervalMs)
                onClickListener.onClick(v)
            }
        }
    }
}

fun View.onThrottleClick(intervalMs: Long = 300L, action: (view: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener, intervalMs))
}