package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.CartRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.CartItemModel
import com.example.shoppingapp.domain.repository.CartRepository
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val itemRemoteDataSource: CartRemoteDataSource
): CartRepository {

    override fun loadCartItemWithDetails(): Flow<Response<List<CartItemModel>>> {
        return itemRemoteDataSource.loadCartItemWithDetails().map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }

    override suspend fun incrementCartItemQuantity(itemId: Int): Response<Unit> {
        return itemRemoteDataSource.incrementCartItemQuantity(itemId)
    }

    override suspend fun decrementCartItemQuantity(itemId: Int): Response<Unit> {
        return itemRemoteDataSource.decrementCartItemQuantity(itemId)
    }

    override suspend fun deleteCartItem(itemId: Int): Response<Unit> {
        return itemRemoteDataSource.deleteCartItem(itemId)
    }
}