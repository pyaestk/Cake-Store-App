package com.example.shoppingapp.presentation.payment

import com.example.shoppingapp.R

// One row item in the cart
data class PaymentItemUiState(
    val title: String,
    val subtitle: String,
    val price: Double,
    val qty: Int,
    val imageRes: Int
) {
    val priceText: String get() = "$" + "%.2f".format(price)
}

enum class ShippingOption { Standard, Express }
enum class PaymentMethod { Card, CashOnDelivery }

// Whole screen state
data class PaymentUiState(
    val shippingAddress: String = "",
    val contactPhone: String = "",
    val contactEmail: String = "",
    val items: List<PaymentItemUiState> = emptyList(),
    val selectedShipping: ShippingOption = ShippingOption.Standard,
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.Card
) {
    val itemCount: Int get() = items.size

    val contactText: String get() = listOf(contactPhone, contactEmail)
        .filter { it.isNotBlank() }
        .joinToString("\n")

    val itemsTotal: Double get() = items.sumOf { it.price * it.qty }

    val shippingFee: Double get() = when (selectedShipping) {
        ShippingOption.Standard -> 0.0
        ShippingOption.Express -> 12.0
    }

    val grandTotal: Double get() = itemsTotal + shippingFee
}

val mockPaymentItems = listOf(
    PaymentItemUiState(
        title = "Cake 1",
        subtitle = "Milk",
        price = 129.00,
        qty = 1,
        imageRes = R.drawable.cupcake
    ),
    PaymentItemUiState(
        title = "Cake 2",
        subtitle = "Dairy",
        price = 159.00,
        qty = 2,
        imageRes = R.drawable.cupcake
    ),
    PaymentItemUiState(
        title = "Cake 3",
        subtitle = "Vegetables",
        price = 249.00,
        qty = 1,
        imageRes = R.drawable.cupcake
    )
)

val mockPaymentUiState = PaymentUiState(
    shippingAddress = """
        Dylan Win
        88/12 Salaya Road
        Phutthamonthon, Nakhon Pathom 73170
        Thailand
    """.trimIndent(),
    contactPhone = "+66 89 123 4567",
    contactEmail = "dylan.win@example.com",
    items = mockPaymentItems,
    selectedShipping = ShippingOption.Standard,
    selectedPaymentMethod = PaymentMethod.Card
)
