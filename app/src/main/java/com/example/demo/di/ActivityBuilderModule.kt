package com.example.demo.di

import com.example.demo.di.module.MovieListModule
import com.example.demo.di.module.MovieViewModelModule
import com.example.demo.ui.main.MovieListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [MovieListModule::class,MovieViewModelModule::class]
    )
    abstract fun bindMainMovieListActivity():MovieListActivity
}