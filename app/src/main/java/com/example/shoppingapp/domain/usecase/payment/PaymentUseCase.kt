package com.example.shoppingapp.domain.usecase.payment

import OrderRequest
import com.example.shoppingapp.domain.model.CheckoutItem
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.PaymentSummaryModel
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.repository.PaymentRepository
import com.example.shoppingapp.domain.usecase.address.GetShippingAddressUseCase
import com.example.shoppingapp.domain.util.Response

class GetPaymentSummaryUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(): Response<PaymentSummaryModel> =
        repo.getPaymentSummary()
}

class GetPaymentSummaryForItemsUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(items: List<CheckoutItem>): Response<PaymentSummaryModel> =
        repo.getPaymentSummaryForItems(items)
}

class SetShippingOptionUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(option: ShippingOption): Response<Unit> =
        repo.setShippingOption(option)
}

class SetPaymentMethodUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(method: PaymentMethod): Response<Unit> =
        repo.setPaymentMethod(method)
}

class SaveOrderUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(request: OrderRequest): Response<String> =
        repo.saveOrder(request)

}

data class PaymentUseCase(
    val getPaymentSummary: GetPaymentSummaryUseCase,
    val getPaymentSummaryForItems: GetPaymentSummaryForItemsUseCase,
    val setShippingOption: SetShippingOptionUseCase,
    val setPaymentMethod: SetPaymentMethodUseCase,
    val getShippingAddressUseCase: GetShippingAddressUseCase,
    val saveOrder: SaveOrderUseCase
)
