package dev.jeffnyauke.covid19stats.di

import dev.jeffnyauke.covid19stats.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}