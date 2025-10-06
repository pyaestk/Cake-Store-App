package com.example.shoppingapp.di

import com.example.shoppingapp.data.remote.AuthRemoteDataSource
import com.example.shoppingapp.data.remote.BannerRemoteDataSource
import com.example.shoppingapp.data.remote.CartRemoteDataSource
import com.example.shoppingapp.data.remote.ItemRemoteDataSource
import com.example.shoppingapp.data.remote.RemoteDatasource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val RemoteDatasourceModule = module {
    single { FirebaseDatabase.getInstance() }
    single { FirebaseAuth.getInstance() }

    single {
        RemoteDatasource(firebaseDatabase = get())
    }

    single {
        ItemRemoteDataSource(firebaseDatabase = get())
    }

    single {
        BannerRemoteDataSource(firebaseDatabase = get())
    }

    single {
        CartRemoteDataSource(firebaseDatabase = get())
    }
    single {
        AuthRemoteDataSource(
            get(),
            auth = get(),
        )
    }
}