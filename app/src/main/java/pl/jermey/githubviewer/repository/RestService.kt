package pl.jermey.githubviewer.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import pl.jermey.githubviewer.model.RepositoryModel
import pl.jermey.githubviewer.model.SearchResults
import pl.jermey.githubviewer.model.UserModel
import pl.jermey.githubviewer.rx.SchedulerProvider
import pl.jermey.githubviewer.rx.schedule

/**
 * Created by Jermey on 25.04.2018.
 */
interface GithubService {
    val zipper: BiFunction<SearchResults<RepositoryModel>, SearchResults<UserModel>, Triple<List<RepositoryModel>, List<UserModel>, Long>>
        get() {
            return BiFunction { r, u -> Triple(r.items, u.items, r.totalCount + u.totalCount) }
        }

    fun search(query: String, page: Int = 0): Observable<Triple<List<RepositoryModel>, List<UserModel>, Long>>
}

class RestService(private val apiService: GithubApi, private val schedulerProvider: SchedulerProvider) : GithubService {

        override fun search(query: String, page: Int): Observable<Triple<List<RepositoryModel>, List<UserModel>, Long>> {
        return Observable.zip(apiService.searchRepositories(query, page), apiService.searchUsers(query, page), zipper)
                .schedule(schedulerProvider)
    }
}