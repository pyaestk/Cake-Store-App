package com.example.shoppingapp.di

import com.example.shoppingapp.data.repository.AuthRepositoryImpl
import com.example.shoppingapp.data.repository.CartRepositoryImpl
import com.example.shoppingapp.data.repository.CategoryRepositoryImpl
import com.example.shoppingapp.data.repository.DetailRepositoryImpl
import com.example.shoppingapp.data.repository.HomeRepositoryImpl
import com.example.shoppingapp.domain.repository.AuthRepository
import com.example.shoppingapp.domain.repository.CartRepository
import com.example.shoppingapp.domain.repository.CategoryRepository
import com.example.shoppingapp.domain.repository.DetailRepository
import com.example.shoppingapp.domain.repository.HomeRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(
            get(),
            get()
        )
    }
    single<CategoryRepository>{
        CategoryRepositoryImpl(
            get(),
            get()
        )
    }
    single<CartRepository>{
        CartRepositoryImpl(
            get()
        )
    }
    single<DetailRepository>{
        DetailRepositoryImpl(
            get(),
            get()
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            get()
        )
    }
}