package pl.jermey.githubviewer.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jermey on 25.04.2018.
 */
class ApplicationSchedulerProvider : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()
}

interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}