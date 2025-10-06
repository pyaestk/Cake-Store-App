package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.BannerRemoteDataSource
import com.example.shoppingapp.data.remote.ItemRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.BannerModel
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.domain.repository.HomeRepository
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val itemRemoteDataSource: ItemRemoteDataSource,
    private val bannerRemoteDataSource: BannerRemoteDataSource
) : HomeRepository {
    override fun loadBanners(): Flow<Response<List<BannerModel>>> {
        return bannerRemoteDataSource.loadBanners().map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }

    override fun loadItems(): Flow<Response<List<ItemModel>>> {
        return itemRemoteDataSource.loadItems().map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }
}

