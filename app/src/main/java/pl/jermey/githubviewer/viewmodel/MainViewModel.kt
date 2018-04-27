package pl.jermey.githubviewer.viewmodel

import android.arch.lifecycle.ViewModel
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import pl.jermey.githubviewer.repository.RestService
import pl.jermey.githubviewer.widget.items.RepositoryItem
import pl.jermey.githubviewer.widget.items.UserItem


class MainViewModel(private val webService: RestService) : ViewModel() {

    val adapter: FastItemAdapter<IItem<*, *>> = FastItemAdapter()

    fun search(query: String) {
        webService.search(query).subscribe({
            adapter.add(it.first.map { RepositoryItem(it) })
            adapter.add(it.second.map { UserItem(it) })
        })
    }

}