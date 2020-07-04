package com.example.demo.di.module

import androidx.lifecycle.ViewModel
import com.example.demo.di.scope.ViewModelKey
import com.example.demo.viewModel.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieListViewModel): ViewModel
}