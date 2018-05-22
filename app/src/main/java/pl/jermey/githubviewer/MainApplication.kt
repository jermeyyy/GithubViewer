package pl.jermey.githubviewer

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import pl.jermey.githubviewer.repository.ApiModule
import pl.jermey.githubviewer.rx.RxModule
import pl.jermey.githubviewer.util.ViewModelInjector

/**
 * Created by Jermey on 25.04.2018.
 */
class MainApplication : Application() {
    companion object {
        fun appModules(test: Boolean = false) = listOf(ApiModule(test), RxModule(), ViewModelInjector())
    }


    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModules())
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}