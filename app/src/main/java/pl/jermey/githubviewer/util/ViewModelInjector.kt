package pl.jermey.githubviewer.util

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import pl.jermey.githubviewer.viewmodel.MainViewModel

class ViewModelInjector : Module {
    override fun invoke() = applicationContext {
        viewModel { MainViewModel(get()) }
    }.invoke()

}