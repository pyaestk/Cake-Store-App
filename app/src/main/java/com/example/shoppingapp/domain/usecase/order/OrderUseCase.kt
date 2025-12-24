package com.example.shoppingapp.domain.usecase.order

import com.example.shoppingapp.domain.repository.OrderRepository

class LoadOrders(private val repository: OrderRepository) {
    operator fun invoke() = repository.loadOrders()
}

data class OrdersUseCase(
    val loadOrders: LoadOrders
)