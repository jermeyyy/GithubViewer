package pl.jermey.githubviewer

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import pl.jermey.githubviewer.viewmodel.MainViewModel
import pl.jermey.githubviewer.viewmodel.SearchEvent


class MainViewModelTest : KoinTest {

    private val query = "test"
    private val errorQuery = "error"
    private val error = Throwable("error")

    private val testSequence = arrayListOf(
            // first page
            SearchEvent(SearchEvent.Type.NEW_QUERY),
            SearchEvent(SearchEvent.Type.LOADING),
            SearchEvent(SearchEvent.Type.SUCCESS),
            // 2nd
            SearchEvent(SearchEvent.Type.LOADING),
            SearchEvent(SearchEvent.Type.SUCCESS),
            // 3rd
            SearchEvent(SearchEvent.Type.LOADING),
            SearchEvent(SearchEvent.Type.SUCCESS)
    )

    private val testErrorSequence = arrayListOf(
            SearchEvent(SearchEvent.Type.NEW_QUERY),
            SearchEvent(SearchEvent.Type.LOADING),
            SearchEvent(SearchEvent.Type.ERROR, error)
    )


    private val viewModel: MainViewModel by inject()

    @Before
    fun before() {
        startKoin(MainApplication.appModules(true))
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testSearch() {
        val testObserver = viewModel.searchEvent.test()
        viewModel.search(query)
        viewModel.search(query, 1)
        viewModel.search(query, 2)
        testObserver.assertValueSequence(testSequence)
        testObserver.assertNoErrors()
    }

    @Test
    fun testSearchError() {
        val testObserver = viewModel.searchEvent.test()
        viewModel.search(errorQuery)
        testObserver.assertValueSequence(testErrorSequence)
    }
}