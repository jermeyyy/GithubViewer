package pl.jermey.githubviewer

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import pl.jermey.githubviewer.repository.ApiModule
import pl.jermey.githubviewer.rx.RxModule

/**
 * Created by Jermey on 25.04.2018.
 */
class MainApplication : Application() {

    private val appModules:List<Module> = listOf(ApiModule(), RxModule())


    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModules)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}