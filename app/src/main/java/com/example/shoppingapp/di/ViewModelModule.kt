package com.example.shoppingapp.di


import com.example.shoppingapp.presentation.MainViewModel
import com.example.shoppingapp.presentation.address.AddressViewModel
import com.example.shoppingapp.presentation.cart.CartScreenViewModel
import com.example.shoppingapp.presentation.category.CategoryScreenViewModel
import com.example.shoppingapp.presentation.detail.DetailScreenViewModel
import com.example.shoppingapp.presentation.fav.FavScreenViewModel
import com.example.shoppingapp.presentation.home.HomeScreenViewModel
import com.example.shoppingapp.presentation.login.LoginViewModel
import com.example.shoppingapp.presentation.payment.PaymentViewModel
import com.example.shoppingapp.presentation.profile.ProfileScreenViewModel
import com.example.shoppingapp.presentation.register.RegisterViewModel
import com.example.shoppingapp.presentation.util.ValidatorImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeScreenViewModel(
            homeUseCases = get()
        )
    }
    viewModel {
        CategoryScreenViewModel(
            get()
        )
    }
    viewModel {
        DetailScreenViewModel(
            get()
        )
    }
    viewModel {
        CartScreenViewModel(
            get(),
        )
    }
    viewModel {
        FavScreenViewModel(
            get()
        )
    }
    viewModel {
        LoginViewModel(
            ValidatorImpl(
                get()
            ),
            get()
        )
    }
    viewModel {
        RegisterViewModel(
            ValidatorImpl(
                get()
            ),
            get()
        )
    }

    viewModel {
        ProfileScreenViewModel(
            get()
        )
    }
    viewModel {
        MainViewModel(
            get()
        )
    }
    viewModel {
        AddressViewModel(
            get()
        )
    }
    viewModel {
        PaymentViewModel(
            get()
        )
    }
}