package com.example.demo.di.module

import androidx.lifecycle.ViewModel
import com.example.demo.di.scope.ViewModelKey
import com.example.demo.viewModel.ApodListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ApodListViewModel::class)
    abstract fun bindMovieViewModel(viewModel: ApodListViewModel): ViewModel
}