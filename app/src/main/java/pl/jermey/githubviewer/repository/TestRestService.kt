package pl.jermey.githubviewer.repository

import io.reactivex.Observable
import pl.jermey.githubviewer.model.RepositoryModel
import pl.jermey.githubviewer.model.UserModel
import pl.jermey.githubviewer.rx.SchedulerProvider
import pl.jermey.githubviewer.rx.schedule

class TestRestService(private val schedulerProvider: SchedulerProvider) : GithubService {

    private val user = UserModel(0, "", "", "")
    private val repo = RepositoryModel(0, "", "", user, "", 0L, 0L,0L)

    override fun search(query: String, page: Int): Observable<Triple<List<RepositoryModel>, List<UserModel>, Long>> {
        return when (query) {
            "test" -> Observable.just(Triple(listOf(repo), listOf(user), 0L))
            else -> Observable.error(Throwable("error"))
        }.schedule(schedulerProvider)
    }
}