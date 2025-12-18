package com.example.shoppingapp.domain.repository

import com.example.shoppingapp.data.model.request.AddAddressRequest
import com.example.shoppingapp.domain.model.AddressModel
import com.example.shoppingapp.domain.util.Response

interface AddressRepository {

    suspend fun saveShippingAddress(
        address: AddAddressRequest
    ): Response<Unit>

    suspend fun getShippingAddress(): Response<AddressModel>

    suspend fun deleteShippingAddress(): Response<Unit>
}
