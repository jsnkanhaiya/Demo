package com.example.demo.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(){

    val compositeDisposable : CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}