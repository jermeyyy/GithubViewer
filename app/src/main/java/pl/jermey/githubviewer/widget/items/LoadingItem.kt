package pl.jermey.githubviewer.widget.items

import android.support.v7.widget.RecyclerView
import android.view.View
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.LoadingItemBinding

class LoadingItem : KAbstractItem<LoadingItem, LoadingItem.ViewHolder>(R.layout.loading_item, ::ViewHolder) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: LoadingItemBinding = LoadingItemBinding.bind(itemView)
    }
}