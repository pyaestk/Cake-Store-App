package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.model.request.AddAddressRequest
import com.example.shoppingapp.data.remote.AddressRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.AddressModel
import com.example.shoppingapp.domain.repository.AddressRepository
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.domain.util.map

class AddressRepositoryImpl(
    private val remote: AddressRemoteDataSource
) : AddressRepository {

    override suspend fun saveShippingAddress(
        address: AddAddressRequest
    ): Response<Unit> {
        return remote.saveShippingAddress(address)
    }

    override suspend fun getShippingAddress(): Response<AddressModel> {
        return remote.getShippingAddress().map { it.toModel() }
    }

    override suspend fun deleteShippingAddress(): Response<Unit> {
        return remote.deleteShippingAddress()
    }
}
