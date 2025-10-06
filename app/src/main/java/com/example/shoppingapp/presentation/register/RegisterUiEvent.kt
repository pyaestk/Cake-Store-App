package com.example.shoppingapp.presentation.register

sealed class RegisterUiEvent {
    data class EmailChanged(val email: String) : RegisterUiEvent()
    data class PasswordChanged(val password: String) : RegisterUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterUiEvent()
    data object Submit : RegisterUiEvent()
}