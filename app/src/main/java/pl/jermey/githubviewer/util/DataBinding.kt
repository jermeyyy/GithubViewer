package pl.jermey.githubviewer.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

object DataBinding {

    @JvmStatic
    @BindingAdapter("urlSrc")
    fun urlSrc(imageView: ImageView, url:String) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }
}