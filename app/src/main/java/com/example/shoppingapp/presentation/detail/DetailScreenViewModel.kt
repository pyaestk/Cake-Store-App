package com.example.shoppingapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.domain.usecase.detail.DetailScreenUseCase
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.presentation.detail.DetailScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val detailScreenUseCase: DetailScreenUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(DetailScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: DetailScreenEvent){
        when(event){
            is DetailScreenEvent.AddItemToCart -> {
                viewModelScope.launch {
                    detailScreenUseCase.addItemToCartUseCase(
                        id = event.id,
                        quantity = event.quantity,
                        size = event.size
                    )
                }
            }
            DetailScreenEvent.RemoveSideEffect -> {

            }

            is DetailScreenEvent.AddRemoveItemToFav -> {
                viewModelScope.launch {
                    /*try {

                    } catch () {

                    }*/

                    val item = detailScreenUseCase.getFavItemByIDUseCase(event.itemModel.id)
                    if (item != null) {
                        deleteItem(event.itemModel)
                    } else {
                        upsertItem(event.itemModel)
                    }

                }
            }
        }
    }

    fun getItemDetail(itemId: Int) {
        viewModelScope.launch {
            detailScreenUseCase.getItemDetailUseCase(itemId)
                .onEach { response ->
                    when (response) {
                        is Response.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                        is Response.Success -> {
                            _state.value = DetailScreenState(isLoading = false, itemModel = response.data)
                        }
                        is Response.Error -> {
                            _state.value = _state.value.copy(isLoading = false, error = response.message)
                        }
                    }
                }.collect()
        }
    }

    private suspend fun upsertItem(itemModel: ItemModel) {
        detailScreenUseCase.addItemToFavUseCase(itemModel)
//        sideEffect = UIComponent.Toast("Article Bookmarked")
    }

    private suspend fun deleteItem(itemModel: ItemModel) {
        detailScreenUseCase.removeItemFromFavUseCase(itemModel)
//        sideEffect = UIComponent.Toast("Article Unbookmarked")
    }

}