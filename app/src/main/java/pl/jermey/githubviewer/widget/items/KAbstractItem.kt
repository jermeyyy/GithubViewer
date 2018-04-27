package pl.jermey.githubviewer.widget.items

import android.annotation.SuppressLint
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mikepenz.fastadapter.IClickable
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.items.AbstractItem

open class KAbstractItem<Item, VH : RecyclerView.ViewHolder>(
        @param:LayoutRes private val layoutRes: Int,
        private val viewHolder: (v: View) -> VH,
        private val type: Int = layoutRes
) : AbstractItem<Item, VH>() where Item : IItem<*, *>, Item : IClickable<*> {

    @SuppressLint("ResourceType")
    final override fun getType(): Int = type

    final override fun getViewHolder(v: View): VH = viewHolder(v)

    final override fun getLayoutRes(): Int = layoutRes

}