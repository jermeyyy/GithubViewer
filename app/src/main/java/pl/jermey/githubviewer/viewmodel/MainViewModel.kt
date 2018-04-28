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

    val empty: ObservableField<Boolean> = ObservableField(true)
    val emptyMessage: ObservableField<String> = ObservableField("No results")
    var query: String = ""
    private var loading = false
    var currentPage = 0
    var totalItemsCount = 0L
    val adapter: FastAdapter<IItem<*, *>> = FastAdapter.with(listOf(itemAdapter, footerAdapter))

    fun search(query: String, page: Int = 0) {
        if (loading || query.isBlank() || (query == this.query && page == 0)) return
        loading = true
        this.query = query
        if (page == 0) {
            itemAdapter.clear()
            currentPage = 0
            totalItemsCount = 0L
        }
        footerAdapter.add(LoadingItem())
        empty.set(false)
        webService.search(this.query, currentPage).subscribe({
            loading = false
            footerAdapter.clear()
            totalItemsCount = it.third
            itemAdapter.add(it.first.map { RepositoryItem(it) })
            itemAdapter.add(it.second.map { UserItem(it) })
            currentPage++
        }, {
            it.printStackTrace()
            loading = false
            currentPage = 0
            totalItemsCount = 0L
            footerAdapter.clear()
            itemAdapter.clear()
            empty.set(true)
            emptyMessage.set("Connection error")
        })
    }

}