package com.example.shoppingapp.presentation.payment

import OrderItemRequest
import OrderRequest
import PaymentUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.model.CheckoutItem
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
        loadAddress()
    }

    fun onEvent(event: PaymentUiEvent) {
        when (event) {
            is PaymentUiEvent.ChangeShipping -> changeShipping(event.option)
            is PaymentUiEvent.ChangePaymentMethod -> changePaymentMethod(event.method)
            PaymentUiEvent.Pay -> pay()
            PaymentUiEvent.ClearError -> _state.update { it.copy(error = null) }
            PaymentUiEvent.OrderSuccess -> _state.update { it.copy(lastOrderId = null) }
            is PaymentUiEvent.LoadForItems -> loadForItems(event.items)
        }
    }

    fun loadAddress() {
        viewModelScope.launch {

            when (val result = useCase.getShippingAddressUseCase()) {
                is Response.Success -> {
                    val a = result.data
                    if (a != null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                shippingAddress = "${a.name}\n${a.addressNum}\n${a.city}, ${a.country}\n${a.zipCode}\n${a.phoneNumber}"
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                    }
                }

                is Response.Error<*> -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to load address"
                        )
                    }
                }

                is Response.Loading<*> -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }

//    private fun load() = viewModelScope.launch {
//        _state.update { it.copy(isLoading = true, error = null) }
//
//        when (val res = useCase.getPaymentSummary()) {
//            is Response.Success -> {
//                val summary = res.data
//                if (summary == null) {
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            error = "No shipping address found. Please add your address."
//                        )
//                    }
//                    return@launch
//                }
//
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        items = summary.items,
//                        selectedShipping = summary.selectedShipping,
//                        selectedPaymentMethod = summary.selectedPaymentMethod,
//                        itemsTotal = summary.itemsTotal,
//                        shippingFee = summary.shippingFee,
//                        grandTotal = summary.grandTotal,
//                        lastOrderId = null
//                    )
//                }
//            }
//
//            is Response.Error -> {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        error = res.message ?: "Unknown error"
//                    )
//                }
//            }
//
//            is Response.Loading<*> -> {
//                _state.update { it.copy(isLoading = true) }
//            }
//        }
//    }

    private fun changeShipping(option: ShippingOption) =
        viewModelScope.launch {
            _state.update { it.copy(selectedShipping = option) }

            when (val res = useCase.setShippingOption(option)) {
                is Response.Success -> refreshSummary()
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
                is Response.Success -> refreshSummary()
                is Response.Error -> _state.update {
                    it.copy(error = res.message ?: "Failed to update payment method")
                }

                is Response.Loading<*> -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }

//    private fun pay() = viewModelScope.launch {
//        _state.update { it.copy(isLoading = true, error = null) }
//
//        when (val res = useCase.pay()) {
//            is Response.Success -> {
//                val result = res.data
//                if (result == null) {
//                    _state.update { it.copy(isLoading = false, error = "Payment failed") }
//                    return@launch
//                }
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        lastOrderId = result.orderId
//                    )
//                }
//            }
//
//            is Response.Error -> {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        error = res.message ?: "Payment failed"
//                    )
//                }
//            }
//
//            is Response.Loading<*> -> {
//                _state.update { it.copy(isLoading = true) }
//            }
//        }
//    }

    private fun pay() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        val s = _state.value
        if (s.items.isEmpty()) {
            _state.update { it.copy(isLoading = false, error = "No items to checkout") }
            return@launch
        }

        val req = OrderRequest(
            itemsTotal = s.itemsTotal,
            shippingFee = s.shippingFee,
            grandTotal = s.grandTotal,
            shippingOption = s.selectedShipping.name,
            paymentMethod = s.selectedPaymentMethod.name,
            items = s.items.map { item ->
                OrderItemRequest(
                    itemId = item.id,
                    title = item.title,
                    subtitle = item.subtitle,
                    imageUrl = item.imageUrl,
                    qty = item.qty,
                    price = item.price
                )
            }
        )

        when (val res = useCase.saveOrder(req)) {
            is Response.Success -> {
                val orderId = res.data ?: run {
                    _state.update { it.copy(isLoading = false, error = "Failed to save order") }
                    return@launch
                }
                _state.update { it.copy(isLoading = false, lastOrderId = orderId) }
            }
            is Response.Error -> _state.update {
                it.copy(isLoading = false, error = res.message ?: "Failed to save order")
            }
            is Response.Loading<*> -> _state.update { it.copy(isLoading = true) }
        }
    }


    private fun refreshSummary() {
        val items = _state.value.checkoutItems
        if (items.isNotEmpty()) {
            loadForItems(items)
        } else {
            _state.update { it.copy(error = "No items to checkout") }
        }
    }


    private fun loadForItems(items: List<CheckoutItem>) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null, checkoutItems = items) }

        when (val res = useCase.getPaymentSummaryForItems(items)) {
            is Response.Success -> {
                val summary = res.data ?: run {
                    _state.update { it.copy(isLoading = false, error = "Failed to load payment") }
                    return@launch
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                        items = summary.items,
                        selectedShipping = summary.selectedShipping,
                        selectedPaymentMethod = summary.selectedPaymentMethod,
                        itemsTotal = summary.itemsTotal,
                        shippingFee = summary.shippingFee,
                        grandTotal = summary.grandTotal,
                        lastOrderId = null
                    )
                }
            }
            is Response.Error -> _state.update { it.copy(isLoading = false, error = res.message ?: "Unknown error") }
            is Response.Loading<*> -> _state.update { it.copy(isLoading = true) }
        }
    }
}
