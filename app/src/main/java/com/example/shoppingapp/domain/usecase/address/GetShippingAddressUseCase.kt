package com.example.shoppingapp.domain.usecase.address

import com.example.shoppingapp.domain.repository.AddressRepository

class GetShippingAddressUseCase(
    private val repository: AddressRepository
) {
    suspend operator fun invoke() = repository.getShippingAddress()
}
