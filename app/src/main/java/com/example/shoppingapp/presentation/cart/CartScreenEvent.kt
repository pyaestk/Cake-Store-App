package com.example.shoppingapp.presentation.cart

sealed class CartScreenEvent {
    data class IncreaseQuantity(val itemId: Int) : CartScreenEvent()
    data class DecreaseQuantity(val itemId: Int) : CartScreenEvent()
    data class RemoveItem(val itemId: Int) : CartScreenEvent()
}