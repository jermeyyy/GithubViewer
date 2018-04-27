package pl.jermey.githubviewer.util

import android.support.v7.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ObservableOnQueryTextListener: SearchView.OnQueryTextListener {

    private val querySubject: BehaviorSubject<String> = BehaviorSubject.create()

    val observable: Observable<String> = querySubject.debounce(300, TimeUnit.MILLISECONDS)

    override fun onQueryTextSubmit(query: String?): Boolean {
        querySubject.onNext(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        querySubject.onNext(newText)
        return true
    }
}