package com.example.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.example.shoppingapp.data.local.database.FavItemDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalRemoteDatasourceModule = module {
    single {
        provideDao(get())
    }
    single {
        provideRoomDatabase(context = androidContext())
    }
}

private fun provideRoomDatabase(context: Context): FavItemDatabase {

    val database: FavItemDatabase?

    database = Room.databaseBuilder(
        context,
        FavItemDatabase::class.java,
        "fav-items.db"
    ).build()

    return database
}

private fun provideDao(database: FavItemDatabase) = database.dao