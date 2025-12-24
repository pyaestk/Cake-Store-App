package com.example.shoppingapp.data.model.response

data class OrderResponse(
    val itemsTotal: Double = 0.0,
    val shippingFee: Double= 0.0,
    val grandTotal: Double= 0.0,
    val shippingOption: String= "",
    val paymentMethod: String= "",
    val items: List<OrderItemResponse> = emptyList()
)

data class OrderItemResponse(
    val itemId: String= "",
    val title: String= "",
    val subtitle: String= "",
    val imageUrl: String? = "",
    val qty: Int= 0,
    val price: Double= 0.0
)