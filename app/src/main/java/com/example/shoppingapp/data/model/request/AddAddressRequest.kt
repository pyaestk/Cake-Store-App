package com.example.shoppingapp.data.model.request

data class AddAddressRequest (
    val name: String = "",
    val addressNum: String = "",
    val city: String = "",
    val country: String = "",
    val zipCode: String = "",
    val phoneNumber: String = "",
)