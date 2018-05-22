package pl.jermey.githubviewer.rx

import io.reactivex.Observable
import org.koin.dsl.context.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * Created by Jermey on 25.04.2018.
 */
class RxModule : Module {
    override fun invoke(): Context = applicationContext {
        bean("appScheduler") { ApplicationSchedulerProvider() } bind (SchedulerProvider::class)
        bean("testScheduler") { TestSchedulerProvider() } bind (SchedulerProvider::class)
    }.invoke()
}

inline fun <reified T> Observable<T>.schedule(schedulerProvider: SchedulerProvider): Observable<T> = this.subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
