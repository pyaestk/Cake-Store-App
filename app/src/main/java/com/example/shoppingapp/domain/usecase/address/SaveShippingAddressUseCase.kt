package com.example.shoppingapp.domain.usecase.address

import com.example.shoppingapp.data.model.request.AddAddressRequest
import com.example.shoppingapp.domain.repository.AddressRepository

class SaveShippingAddressUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(
        address: AddAddressRequest
    ) = repository.saveShippingAddress(address)
}
