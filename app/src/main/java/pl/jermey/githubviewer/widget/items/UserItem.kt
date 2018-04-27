package pl.jermey.githubviewer.widget.items

import android.support.v7.widget.RecyclerView
import android.view.View
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.UserItemBinding
import pl.jermey.githubviewer.model.UserModel

class UserItem(private val userModel: UserModel) : KAbstractItem<UserItem, UserItem.ViewHolder>(R.layout.user_item, ::ViewHolder) {

    override fun bindView(holder: UserItem.ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder.binding.user = userModel
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: UserItemBinding = UserItemBinding.bind(itemView)
    }
}