package com.example.shoppingapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class ShippingOption { Standard, Express }
enum class PaymentMethod { Card, CashOnDelivery }
@Parcelize
data class PaymentItemModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val qty: Int,
    val price: Double
): Parcelable

data class PaymentSummaryModel(
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
