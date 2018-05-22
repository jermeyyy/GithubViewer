package pl.jermey.githubviewer.view

import android.databinding.DataBindingUtil
import android.databinding.OnRebindCallback
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil
import com.mikepenz.fastadapter.utils.ComparableItemListImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.MainActivityBinding
import pl.jermey.githubviewer.rx.ApplicationSchedulerProvider
import pl.jermey.githubviewer.rx.schedule
import pl.jermey.githubviewer.util.ObservableOnQueryTextListener
import pl.jermey.githubviewer.util.PagingScrollListener
import pl.jermey.githubviewer.viewmodel.MainViewModel
import pl.jermey.githubviewer.viewmodel.SearchEvent
import pl.jermey.githubviewer.widget.items.KAbstractItem
import pl.jermey.githubviewer.widget.items.LoadingItem
import pl.jermey.githubviewer.widget.items.RepositoryItem
import pl.jermey.githubviewer.widget.items.UserItem

typealias ListItem = KAbstractItem<*, *>

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val schedulerProvider: ApplicationSchedulerProvider by inject()
    private lateinit var binding: MainActivityBinding

    private val comparator = Comparator<ListItem> { t1, t2 -> t1.getIdentifier().compareTo(t2.getIdentifier()) }
    private val itemAdapter = ItemAdapter<ListItem>(ComparableItemListImpl<ListItem>(comparator))
    private val footerAdapter = ItemAdapter<ListItem>()
    private val adapter: FastAdapter<ListItem> = FastAdapter.with(listOf(FastAdapterDiffUtil.set(itemAdapter, emptyList()), footerAdapter))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = mainViewModel
        binding.adapter = adapter
        val listener = ObservableOnQueryTextListener()
        listener.observable.schedule(schedulerProvider)
                .subscribe { mainViewModel.search(it) }
        mainViewModel.searchEvent.subscribe(::handleSearchEvent)
        binding.searchView.setOnQueryTextListener(listener)
        binding.addOnRebindCallback(object : OnRebindCallback<MainActivityBinding>() {
            override fun onBound(binding: MainActivityBinding?) {
                super.onBound(binding)
                binding?.recyclerView?.addOnScrollListener(PagingScrollListener(mainViewModel))
                binding?.removeOnRebindCallback(this)
            }
        })
    }

    private fun getList() = mainViewModel.repositories.map { RepositoryItem(it) } + mainViewModel.users.map { UserItem(it) }

    private fun handleSearchEvent(searchEvent: SearchEvent) {
        when (searchEvent.type) {
            SearchEvent.Type.LOADING -> {
                footerAdapter.add(LoadingItem())
            }
            SearchEvent.Type.NEW_QUERY -> {
                itemAdapter.clear()
            }
            SearchEvent.Type.SUCCESS -> {
                footerAdapter.clear()
                val diff = FastAdapterDiffUtil.calculateDiff(itemAdapter, getList())
                FastAdapterDiffUtil.set(itemAdapter, diff)
            }
            SearchEvent.Type.ERROR -> {
                footerAdapter.clear()
                itemAdapter.clear()
            }
        }
    }

}
