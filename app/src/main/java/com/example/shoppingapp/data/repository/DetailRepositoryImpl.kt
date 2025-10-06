package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.CartRemoteDataSource
import com.example.shoppingapp.data.remote.ItemRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.domain.repository.DetailRepository
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailRepositoryImpl(
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val itemRemoteDataSource: ItemRemoteDataSource
): DetailRepository {
    override suspend fun addItemToCart(
        id: Int,
        quantity: Int,
        size: String
    ): Response<Unit> {
        return cartRemoteDataSource.addItemToCart(id, quantity, size)
    }

    override fun loadItemDetail(itemId: Int): Flow<Response<ItemModel>> {
        return itemRemoteDataSource.loadItemDetail(itemId).map {
            when (it) {
                is Response.Success -> Response.Success(it.data?.toModel())
                is Response.Error -> Response.Error(it.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }
}