package com.example.shoppingapp.di

import OrderRemoteDataSource
import com.example.shoppingapp.data.remote.AddressRemoteDataSource
import com.example.shoppingapp.data.remote.AuthRemoteDataSource
import com.example.shoppingapp.data.remote.BannerRemoteDataSource
import com.example.shoppingapp.data.remote.CartRemoteDataSource
import com.example.shoppingapp.data.remote.ItemRemoteDataSource
import com.example.shoppingapp.data.remote.PaymentRemoteDataSource
import com.example.shoppingapp.data.remote.RemoteDatasource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val RemoteDatasourceModule = module {
    single { FirebaseDatabase.getInstance() }
    single { FirebaseFirestore.getInstance() }
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
        CartRemoteDataSource(firebaseDatabase = get(), firebaseFirestore = get(), auth = get())
    }
    single {
        AuthRemoteDataSource(
            get(),
            auth = get(),
        )
    }

    single {
        AddressRemoteDataSource(
            get(), get()
        )
    }
    single {
        PaymentRemoteDataSource(
            get(), get(), get()
        )
    }

    single {
        OrderRemoteDataSource(
            get(), get()
        )
    }
}