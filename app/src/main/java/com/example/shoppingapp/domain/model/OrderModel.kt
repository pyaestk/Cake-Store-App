package com.example.shoppingapp.domain.model

data class OrderModel(
    val itemsTotal: Double,
    val shippingFee: Double,
    val grandTotal: Double,
    val shippingOption: String,
    val paymentMethod: String,
    val items: List<OrderItemModel>
)

data class OrderItemModel(
    val itemId: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val qty: Int,
    val price: Double
)