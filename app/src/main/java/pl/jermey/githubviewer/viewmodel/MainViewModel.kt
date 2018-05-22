package pl.jermey.githubviewer.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableLong
import io.reactivex.subjects.PublishSubject
import pl.jermey.githubviewer.model.RepositoryModel
import pl.jermey.githubviewer.model.UserModel
import pl.jermey.githubviewer.repository.GithubService


class MainViewModel(private val webService: GithubService) : ViewModel() {

    val empty: ObservableField<Boolean> = ObservableField(true)
    val emptyMessage: ObservableField<String> = ObservableField("No results")
    private var loading = false
    var query: String = ""

    var currentPage = 0
    var totalItemsCount = ObservableLong(0L)
    var repositories: List<RepositoryModel> = emptyList()
    var users: List<UserModel> = emptyList()

    val searchEvent: PublishSubject<SearchEvent> = PublishSubject.create()

    fun search(query: String, page: Int = 0) {
        if (loading || query.isBlank() || (query == this.query && page == 0)) return
        if (page == 0) {
            searchEvent.onNext(SearchEvent(SearchEvent.Type.NEW_QUERY))
            clearData()
        }
        this.query = query
        loading = true
        empty.set(false)
        searchEvent.onNext(SearchEvent(SearchEvent.Type.LOADING))
        webService.search(this.query, currentPage).subscribe({
            loading = false
            totalItemsCount.set(it.third)
            repositories += it.first
            users += it.second
            searchEvent.onNext(SearchEvent(SearchEvent.Type.SUCCESS))
            currentPage++
        }, {
            clearData()
            empty.set(true)
            emptyMessage.set("Connection error")
            searchEvent.onNext(SearchEvent(SearchEvent.Type.ERROR, it))
        })
    }

    private fun clearData() {
        loading = false
        query = ""
        currentPage = 0
        totalItemsCount.set(0L)
        repositories = emptyList()
        users = emptyList()
    }

}

@Suppress("EqualsOrHashCode")
data class SearchEvent(val type: Type,
                       val error: Throwable? = null) {
    enum class Type {
        LOADING, SUCCESS, NEW_QUERY, ERROR
    }

    override fun equals(other: Any?): Boolean {
        return this.type == (other as SearchEvent).type
    }
}