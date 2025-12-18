package com.example.shoppingapp.presentation.payment

import PaymentUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.model.ShippingOption
import com.example.shoppingapp.domain.usecase.payment.PaymentUseCase
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val useCase: PaymentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PaymentUiState())
    val state: StateFlow<PaymentUiState> = _state


    init {
        load()
    }

    fun onEvent(event: PaymentUiEvent) {
        when (event) {
            is PaymentUiEvent.ChangeShipping -> changeShipping(event.option)
            is PaymentUiEvent.ChangePaymentMethod -> changePaymentMethod(event.method)
            PaymentUiEvent.Pay -> pay()
            PaymentUiEvent.ClearError -> _state.update { it.copy(error = null) }
            PaymentUiEvent.OrderSuccess ->  _state.update { it.copy(lastOrderId = null) }
        }
    }

    private fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        when (val res = useCase.getPaymentSummary()) {
            is Response.Success -> {
                val summary = res.data
                if (summary == null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "No shipping address found. Please add your address."
                        )
                    }
                    return@launch
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        items = summary.items,
                        shippingAddress = summary.shippingAddress,
                        selectedShipping = summary.selectedShipping,
                        selectedPaymentMethod = summary.selectedPaymentMethod,
                        itemsTotal = summary.itemsTotal,
                        shippingFee = summary.shippingFee,
                        grandTotal = summary.grandTotal,
                        lastOrderId = null
                    )
                }
            }

            is Response.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = res.message ?: "Unknown error"
                    )
                }
            }

            is Response.Loading<*> -> {
                _state.update { it.copy(isLoading = true) }
            }
        }
    }

    private fun changeShipping(option: ShippingOption) =
        viewModelScope.launch {
            // optimistic UI update
            _state.update { it.copy(selectedShipping = option) }

            when (val res = useCase.setShippingOption(option)) {
                is Response.Success -> load() // refresh totals
                is Response.Error -> _state.update {
                    it.copy(error = res.message ?: "Failed to update shipping")
                }

                is Response.Loading<*> -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }

    private fun changePaymentMethod(method: com.example.shoppingapp.domain.model.PaymentMethod) =
        viewModelScope.launch {
            _state.update { it.copy(selectedPaymentMethod = method) }

            when (val res = useCase.setPaymentMethod(method)) {
                is Response.Success -> Unit
                is Response.Error -> _state.update {
                    it.copy(error = res.message ?: "Failed to update payment method")
                }

                is Response.Loading<*> -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }

    private fun pay() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        when (val res = useCase.pay()) {
            is Response.Success -> {
                val result = res.data
                if (result == null) {
                    _state.update { it.copy(isLoading = false, error = "Payment failed") }
                    return@launch
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                        lastOrderId = result.orderId
                    )
                }
            }

            is Response.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = res.message ?: "Payment failed"
                    )
                }
            }

            is Response.Loading<*> -> {
                _state.update { it.copy(isLoading = true) }
            }
        }
    }
}
