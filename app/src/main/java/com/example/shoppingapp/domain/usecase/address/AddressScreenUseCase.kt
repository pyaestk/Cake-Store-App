package com.example.shoppingapp.domain.usecase.address

data class AddressScreenUseCase (
    val getShippingAddressUseCase: GetShippingAddressUseCase,
    val saveShippingAddressUseCase: SaveShippingAddressUseCase,
)