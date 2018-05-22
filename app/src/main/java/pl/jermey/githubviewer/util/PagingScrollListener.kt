package pl.jermey.githubviewer.util

import android.os.Handler
import android.os.Looper
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener
import pl.jermey.githubviewer.viewmodel.MainViewModel

class PagingScrollListener(private val mainViewModel: MainViewModel) : EndlessRecyclerOnScrollListener() {
    override fun onLoadMore(currentPage: Int) {
        if (mainViewModel.currentPage * 30 < mainViewModel.totalItemsCount.get()) {
            Handler(Looper.getMainLooper()).post { mainViewModel.search(mainViewModel.query, mainViewModel.currentPage) }
        }
    }

    override fun getCurrentPage(): Int = mainViewModel.currentPage
}