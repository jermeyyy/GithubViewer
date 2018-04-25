package pl.jermey.githubviewer.repository

import io.reactivex.Observable
import pl.jermey.githubviewer.model.RepositoryModel
import pl.jermey.githubviewer.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jermey on 25.04.2018.
 */
interface GithubApi {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Observable<SearchResult<RepositoryModel>>

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Observable<SearchResult<UserModel>>
}