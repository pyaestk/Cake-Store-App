package com.example.shoppingapp.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.usecase.order.OrdersUseCase
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OrderScreenViewModel(
    private val useCase: OrdersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderScreenUiState())
    val state = _state.asStateFlow()

    fun loadOrders() {
        useCase.loadOrders()
            .onEach { response ->
                when (response) {
                    is Response.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, error = null)
                    }

                    is Response.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = response.message ?: "An unknown error occurred"
                        )
                    }

                    is Response.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null,
                            orders = response.data ?: emptyList()
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}
