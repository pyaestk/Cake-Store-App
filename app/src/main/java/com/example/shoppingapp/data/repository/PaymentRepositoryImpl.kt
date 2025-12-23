package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.PaymentRemoteDataSource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PayResultModel
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.PaymentSummaryModel
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.repository.PaymentRepository
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.domain.util.map

class PaymentRepositoryImpl(
    private val remote: PaymentRemoteDataSource
) : PaymentRepository {

    override suspend fun getPaymentSummary(): Response<PaymentSummaryModel> =
        remote.getPaymentSummary().map { it.toModel() }

    override suspend fun setShippingOption(option: ShippingOption): Response<Unit> =
        remote.setShippingOption(option)

    override suspend fun setPaymentMethod(method: PaymentMethod): Response<Unit> =
        remote.setPaymentMethod(method)

    override suspend fun pay(): Response<PayResultModel> =
        remote.pay().map { it.toModel() }

    override suspend fun getPaymentSummaryForItems(items: List<CheckoutItem>): Response<PaymentSummaryModel> =
        remote.getPaymentSummaryForItems(items).map { it.toModel() }
}
