package com.example.shoppingapp.presentation.address

data class AddressUiState(
    val name: String = "",
    val addressNum: String = "",
    val city: String = "",
    val country: String = "",
    val zipCode: String = "",
    val phoneNumber: String = "",

    val nameError: String? = null,
    val addressError: String? = null,
    val cityError: String? = null,
    val countryError: String? = null,
    val postCodeError: String? = null,
    val phoneNumError: String? = null,

    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val error: String? = null
)
