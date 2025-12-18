package com.example.shoppingapp.domain.model

enum class ShippingOption { Standard, Express }
enum class PaymentMethod { Card, CashOnDelivery }

data class PaymentItemModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val qty: Int,
    val price: Double
)

data class PaymentSummaryModel(
    val shippingAddress: String,
    val items: List<PaymentItemModel>,
    val selectedShipping: ShippingOption,
    val selectedPaymentMethod: PaymentMethod,
    val itemsTotal: Double,
    val shippingFee: Double,
    val grandTotal: Double
)

data class PayResultModel(
    val orderId: String,
    val status: String
)
