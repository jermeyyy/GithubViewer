package pl.jermey.githubviewer.widget.items

import android.support.v7.widget.RecyclerView
import android.view.View
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.RepositoryItemBinding
import pl.jermey.githubviewer.model.RepositoryModel

class RepositoryItem(private val repository: RepositoryModel) : KAbstractItem<RepositoryItem, RepositoryItem.ViewHolder>(R.layout.repository_item, ::ViewHolder) {

    override fun bindView(holder: ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder.binding.repository = repository
    }

    override fun getIdentifier() = repository.id

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: RepositoryItemBinding = RepositoryItemBinding.bind(itemView)
    }

}
