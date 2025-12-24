package com.example.shoppingapp.presentation.order

import com.example.shoppingapp.domain.model.OrderModel

data class OrderScreenUiState(
    val orders: List<OrderModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
