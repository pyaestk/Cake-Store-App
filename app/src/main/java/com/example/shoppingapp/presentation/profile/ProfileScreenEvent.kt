package com.example.shoppingapp.presentation.profile


sealed class ProfileScreenEvent {
    object Logout : ProfileScreenEvent()
    object NavigateToAppStart : ProfileScreenEvent()
}
