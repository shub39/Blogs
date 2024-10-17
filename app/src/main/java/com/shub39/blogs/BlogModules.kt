package com.shub39.blogs

import com.shub39.blogs.vm.BlogVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blogModules = module {
    viewModel { BlogVM(get()) }
}