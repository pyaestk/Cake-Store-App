package com.example.shoppingapp.presentation.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.model.request.AddAddressRequest
import com.example.shoppingapp.domain.usecase.address.AddressScreenUseCase
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.presentation.util.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddressViewModel(
    private val validator: Validator,
    private val useCase: AddressScreenUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddressUiState())
    val addressFormState = _uiState.asStateFlow()

    init {
        loadAddress()
    }

    fun onEvent(event: AddressUiEvent) {
        when (event) {
            is AddressUiEvent.NameChanged ->
                _uiState.update { it.copy(name = event.name, error = null, isFormValid = false) }

            is AddressUiEvent.AddressChanged ->
                _uiState.update { it.copy(addressNum = event.address, error = null, isFormValid = false) }

            is AddressUiEvent.CityChanged ->
                _uiState.update { it.copy(city = event.city, error = null, isFormValid = false) }

            is AddressUiEvent.PostCodeChanged ->
                _uiState.update { it.copy(zipCode = event.postCode, error = null, isFormValid = false) }

            is AddressUiEvent.PhoneNumChanged ->
                _uiState.update { it.copy(phoneNumber = event.phoneNum, error = null, isFormValid = false) }

            is AddressUiEvent.CountryChanged ->
                _uiState.update { it.copy(country = event.country, error = null, isFormValid = false) }

            AddressUiEvent.Submit -> {
                val name = _uiState.value.name
                val address = _uiState.value.addressNum
                val city = _uiState.value.city
                val country = _uiState.value.country
                val postCode = _uiState.value.zipCode
                val phoneNum = _uiState.value.phoneNumber

                val nameResult = validator.emptyValidator(name)
                val addressResult = validator.emptyValidator(address)
                val cityResult = validator.emptyValidator(city)
                val countryResult = validator.emptyValidator(country)
                val postCodeResult = validator.emptyValidator(postCode)
                val phoneNumResult = validator.emptyValidator(phoneNum)

                if (
                    nameResult.successful &&
                    addressResult.successful &&
                    cityResult.successful &&
                    countryResult.successful &&
                    postCodeResult.successful &&
                    phoneNumResult.successful
                ) {
                    submit()
                } else {
                    _uiState.update {
                        it.copy(
                            nameError = nameResult.errorMessage,
                            addressError = addressResult.errorMessage,
                            cityError = cityResult.errorMessage,
                            countryError = countryResult.errorMessage,
                            postCodeError = postCodeResult.errorMessage,
                            phoneNumError = phoneNumResult.errorMessage,
                            isFormValid = false
                        )
                    }
                }
            }
        }
    }

    private fun loadAddress() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = useCase.getShippingAddressUseCase()) {
                is Response.Success -> {
                    val a = result.data
                    if (a != null) {
                        _uiState.update {
                            it.copy(
                                name = a.name,
                                addressNum = a.addressNum,
                                city = a.city,
                                country = a.country,
                                zipCode = a.zipCode,
                                phoneNumber = a.phoneNumber,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }

                is Response.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to load address"
                        )
                    }
                }

                is Response.Loading<*> -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun submit() {
        val s = _uiState.value

        val request = AddAddressRequest(
            name = s.name.trim(),
            addressNum = s.addressNum.trim(),
            city = s.city.trim(),
            country = s.country.trim(),
            zipCode = s.zipCode.trim(),
            phoneNumber = s.phoneNumber.trim()
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = useCase.saveShippingAddressUseCase(request)) {
                is Response.Success -> {
                    _uiState.update {
                        it.copy(
                            isFormValid = true,
                            isLoading = false
                        )
                    }
                }

                is Response.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Save address failed"
                        )
                    }
                }

                is Response.Loading<*> -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
