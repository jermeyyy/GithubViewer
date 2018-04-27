package pl.jermey.githubviewer.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import pl.jermey.githubviewer.model.RepositoryModel
import pl.jermey.githubviewer.model.SearchResults
import pl.jermey.githubviewer.model.UserModel
import pl.jermey.githubviewer.rx.ApplicationSchedulerProvider
import pl.jermey.githubviewer.rx.schedule

/**
 * Created by Jermey on 25.04.2018.
 */
class RestService(private val apiService: GithubApi, private val schedulerProvider: ApplicationSchedulerProvider) {
    fun search(query: String): Observable<Pair<List<RepositoryModel>, List<UserModel>>> {
        val zipper = BiFunction<SearchResults<RepositoryModel>, SearchResults<UserModel>, Pair<List<RepositoryModel>, List<UserModel>>> { r, u -> Pair(r.items, u.items) }
        return Observable.zip(apiService.searchRepositories(query), apiService.searchUsers(query), zipper)
                .schedule(schedulerProvider)
    }
}