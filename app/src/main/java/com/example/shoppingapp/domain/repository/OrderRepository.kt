package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.domain.model.OrderModel
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun loadOrders(): Flow<Response<List<OrderModel>>>
}