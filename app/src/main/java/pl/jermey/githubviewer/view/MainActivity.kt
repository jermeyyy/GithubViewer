package pl.jermey.githubviewer.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.MainActivityBinding
import pl.jermey.githubviewer.rx.ApplicationSchedulerProvider
import pl.jermey.githubviewer.rx.schedule
import pl.jermey.githubviewer.util.ObservableOnQueryTextListener
import pl.jermey.githubviewer.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val schedulerProvider: ApplicationSchedulerProvider by inject()
    private lateinit var binding: MainActivityBinding

    private val scrollListener: EndlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener() {
        override fun onLoadMore(currentPage: Int) {
            if (currentPage * 30 < mainViewModel.totalItemsCount) {
                mainViewModel.search(binding.searchView.query.toString(), mainViewModel.currentPage)
            }
        }

        override fun getCurrentPage(): Int = mainViewModel.currentPage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = mainViewModel
        val listener = ObservableOnQueryTextListener()
        listener.observable.schedule(schedulerProvider).subscribe { mainViewModel.search(it) }
        binding.searchView.setOnQueryTextListener(listener)
        binding.recyclerView.addOnScrollListener(scrollListener)
    }
}
