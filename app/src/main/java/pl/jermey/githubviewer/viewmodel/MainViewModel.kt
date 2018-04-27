package pl.jermey.githubviewer.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.utils.ComparableItemListImpl
import pl.jermey.githubviewer.repository.RestService
import pl.jermey.githubviewer.widget.items.LoadingItem
import pl.jermey.githubviewer.widget.items.RepositoryItem
import pl.jermey.githubviewer.widget.items.UserItem
import java.util.*


class MainViewModel(private val webService: RestService) : ViewModel() {

    private val comparator: Comparator<IItem<*, *>>? = kotlin.Comparator { t1, t2 -> t1.identifier.compareTo(t2.identifier) }
    private val itemAdapter = ItemAdapter<IItem<*, *>>(ComparableItemListImpl<IItem<*, *>>(comparator))
    private val footerAdapter: ItemAdapter<IItem<*, *>> = ItemAdapter()

    var empty: ObservableField<Boolean> = ObservableField(true)
    val adapter: FastAdapter<IItem<*, *>> = FastAdapter.with(listOf(itemAdapter, footerAdapter))

    fun search(query: String, page: Int = 0) {
        footerAdapter.add(LoadingItem())
        webService.search(query, page).subscribe({
            empty.set(false)
            footerAdapter.clear()
            itemAdapter.add(it.first.map { RepositoryItem(it) })
            itemAdapter.add(it.second.map { UserItem(it) })
        })
    }

}