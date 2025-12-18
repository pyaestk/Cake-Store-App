package com.example.shoppingapp.presentation.address

sealed class AddressUiEvent {
    data class NameChanged(val name: String) : AddressUiEvent()
    data class AddressChanged(val address: String) : AddressUiEvent()
    data class CityChanged(val city: String) : AddressUiEvent()
    data class CountryChanged(val country: String) : AddressUiEvent()
    data class PostCodeChanged(val postCode: String) : AddressUiEvent()
    data class PhoneNumChanged(val phoneNum: String) : AddressUiEvent()

    data object Submit: AddressUiEvent()

}