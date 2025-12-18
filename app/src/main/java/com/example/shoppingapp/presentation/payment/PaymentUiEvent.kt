package com.example.shoppingapp.presentation.payment

import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.ShippingOption

sealed interface PaymentUiEvent {
    data class ChangeShipping(val option: ShippingOption) : PaymentUiEvent
    data class ChangePaymentMethod(val method: PaymentMethod) : PaymentUiEvent

    data object Pay : PaymentUiEvent
    data object ClearError : PaymentUiEvent

    data object OrderSuccess : PaymentUiEvent
}
