package pl.jermey.githubviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.koin.android.ext.android.inject
import pl.jermey.githubviewer.repository.RestService

class MainActivity : AppCompatActivity() {

    val webService: RestService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        webService.search("test").subscribe({
            it.first.forEach { Log.d("R", it.fullName) }
            it.second.forEach { Log.d("U", it.login) }
        })
    }
}
