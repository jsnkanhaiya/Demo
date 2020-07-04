package com.example.demo.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo.R
import com.example.demo.base.ViewModelProviderFactory
import com.example.demo.ui.main.adapters.MovieListAdapter
import com.example.demo.viewModel.MovieListViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MovieListActivity : DaggerAppCompatActivity() {

    private var movieListViewModel : MovieListViewModel? = null
    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var movieListAdapter : MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieListViewModel =
            ViewModelProvider(this,providerFactory).get(MovieListViewModel::class.java)


        if (getResources().getConfiguration().orientation === Configuration.ORIENTATION_PORTRAIT) {
            moviesRecyclerView?.layoutManager = GridLayoutManager(this,  3)
        }
        else {
            moviesRecyclerView?.layoutManager = GridLayoutManager(this,  7)
        }


        observeData()
        movieListViewModel?.showToast()
        movieListViewModel?.parseJsonMovieData()

    }

    fun observeData(){
        movieListViewModel?.strtext?.observe(this, Observer {

            Toast.makeText(this,it,Toast.LENGTH_LONG).show()

        })

        movieListViewModel?.movieList?.observe(this, Observer {


            moviesRecyclerView.adapter = movieListAdapter


        })
    }

   /* override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation==1){
            Toast.makeText(this,"Potrait",Toast.LENGTH_LONG).show()
            moviesRecyclerView?.layoutManager = GridLayoutManager(this,  3)
        }
        if (newConfig.orientation==2){
            Toast.makeText(this,"landscapes",Toast.LENGTH_LONG).show()
            moviesRecyclerView?.layoutManager = GridLayoutManager(this,  7)
    }}*/

}