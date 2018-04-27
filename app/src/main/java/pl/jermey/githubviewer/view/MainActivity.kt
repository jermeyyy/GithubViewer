package pl.jermey.githubviewer.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.koin.android.architecture.ext.viewModel
import pl.jermey.githubviewer.R
import pl.jermey.githubviewer.databinding.MainActivityBinding
import pl.jermey.githubviewer.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()
    lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewModel = mainViewModel
        mainViewModel.search("test")
    }
}
