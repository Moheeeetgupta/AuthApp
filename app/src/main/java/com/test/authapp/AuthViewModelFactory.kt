package com.test.authapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object AuthViewModelFactory {

    inline fun viewModelFactory(crossinline f: () -> ViewModel) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
        }

}