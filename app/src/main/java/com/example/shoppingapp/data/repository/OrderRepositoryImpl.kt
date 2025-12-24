package com.example.shoppingapp.data.repository

import OrderRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.OrderModel
import com.example.shoppingapp.domain.repository.OrderRepository
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepositoryImpl(val remoteDataSource: OrderRemoteDataSource): OrderRepository {
    override fun loadOrders(): Flow<Response<List<OrderModel>>> {
        return remoteDataSource.loadOrders().map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }
}