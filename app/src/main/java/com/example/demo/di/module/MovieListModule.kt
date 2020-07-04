package com.example.demo.di.module

import com.example.demo.ui.main.adapters.MovieListAdapter
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {

    @Provides
    fun provideMovieListAdapter() = MovieListAdapter()
}