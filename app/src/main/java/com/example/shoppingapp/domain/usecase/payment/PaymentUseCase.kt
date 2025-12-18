package com.example.shoppingapp.domain.usecase.payment

import com.example.shoppingapp.domain.model.PayResultModel
import com.example.shoppingapp.domain.model.PaymentMethod
import com.example.shoppingapp.domain.model.PaymentSummaryModel
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.repository.PaymentRepository
import com.example.shoppingapp.domain.util.Response

class GetPaymentSummaryUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(): Response<PaymentSummaryModel> =
        repo.getPaymentSummary()
}

class SetShippingOptionUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(option: ShippingOption): Response<Unit> =
        repo.setShippingOption(option)
}

class SetPaymentMethodUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(method: PaymentMethod): Response<Unit> =
        repo.setPaymentMethod(method)
}

class PayUseCase(private val repo: PaymentRepository) {
    suspend operator fun invoke(): Response<PayResultModel> =
        repo.pay()
}

data class PaymentUseCase(
    val getPaymentSummary: GetPaymentSummaryUseCase,
    val setShippingOption: SetShippingOptionUseCase,
    val setPaymentMethod: SetPaymentMethodUseCase,
    val pay: PayUseCase
)
