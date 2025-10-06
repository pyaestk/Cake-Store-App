package com.example.shoppingapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.domain.repository.AuthRepository
import com.example.shoppingapp.domain.util.Response
import com.example.shoppingapp.presentation.util.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validator: Validator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerFormState = _registerUiState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.EmailChanged -> {
                _registerUiState.update { it.copy(email = event.email, emailError = null) }
            }
            is RegisterUiEvent.PasswordChanged -> {
                _registerUiState.update { it.copy(password = event.password, passwordError = null) }
            }
            is RegisterUiEvent.ConfirmPasswordChanged -> {
                _registerUiState.update { it.copy(confirmPassword = event.confirmPassword, passwordError = null) }
            }
            RegisterUiEvent.Submit -> {
                val email = _registerUiState.value.email
                val password = _registerUiState.value.password
                val confirmPw = _registerUiState.value.confirmPassword

                val emailResult = validator.emailValidator(email)
                val passwordResult = validator.passwordValidator(password)
                val confirmPwResult = validator.confirmPasswordValidator(password = password, confirmPassword = confirmPw)

                if (emailResult.successful && passwordResult.successful && confirmPwResult.successful) {
                    submitData(email, password)
                }
                else {
                    _registerUiState.update {
                        it.copy(
                            emailError = emailResult.errorMessage,
                            passwordError = passwordResult.errorMessage
                        )
                    }
                }
            }

        }
    }

    private fun submitData(email: String, password: String) {
        viewModelScope.launch {
            _registerUiState.update { it.copy(isLoading = true, error = null) }

            when (val result = authRepository.createAccountWithEmail(email, password)) {
                is Response.Success -> {
                    _registerUiState.update {
                        it.copy(
                            isFormValid = true,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Response.Error -> {
                    _registerUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Register failed"
                        )
                    }
                }

                is Response.Loading<*> -> {
                    _registerUiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }

}