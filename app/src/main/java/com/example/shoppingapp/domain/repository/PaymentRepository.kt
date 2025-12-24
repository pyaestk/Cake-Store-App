package com.example.shoppingapp.domain.repository

import OrderRequest
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.PaymentSummaryModel
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.util.Response

interface PaymentRepository {
    suspend fun getPaymentSummary(): Response<PaymentSummaryModel>
    suspend fun setShippingOption(option: ShippingOption): Response<Unit>
    suspend fun setPaymentMethod(method: PaymentMethod): Response<Unit>
    suspend fun saveOrder(request: OrderRequest): Response<String> // orderId
    suspend fun getPaymentSummaryForItems(items: List<CheckoutItem>): Response<PaymentSummaryModel>

}
