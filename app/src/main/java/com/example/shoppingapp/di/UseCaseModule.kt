package com.example.shoppingapp.di

import com.example.shoppingapp.domain.usecase.address.AddressScreenUseCase
import com.example.shoppingapp.domain.usecase.address.GetShippingAddressUseCase
import com.example.shoppingapp.domain.usecase.address.SaveShippingAddressUseCase
import com.example.shoppingapp.domain.usecase.cart.CartScreenUseCase
import com.example.shoppingapp.domain.usecase.cart.DecreaseCartItemQtyUseCase
import com.example.shoppingapp.domain.usecase.cart.GetCartItemUseCase
import com.example.shoppingapp.domain.usecase.cart.IncreaseCartItemQtyUseCase
import com.example.shoppingapp.domain.usecase.cart.RemoveCartItemUseCase
import com.example.shoppingapp.domain.usecase.category.GetItemsByCategoryUseCase
import com.example.shoppingapp.domain.usecase.detail.AddItemToCartUseCase
import com.example.shoppingapp.domain.usecase.detail.AddItemToFavUseCase
import com.example.shoppingapp.domain.usecase.detail.DetailScreenUseCase
import com.example.shoppingapp.domain.usecase.detail.GetFavItemByIDUseCase
import com.example.shoppingapp.domain.usecase.detail.GetItemDetailUseCase
import com.example.shoppingapp.domain.usecase.detail.RemoveItemFromFavUseCase
import com.example.shoppingapp.domain.usecase.fav.FavScreenUseCase
import com.example.shoppingapp.domain.usecase.fav.GetFavItemsUseCase
import com.example.shoppingapp.domain.usecase.home.GetBannersUseCase
import com.example.shoppingapp.domain.usecase.home.GetCategoryUseCase
import com.example.shoppingapp.domain.usecase.home.GetItemsUseCase
import com.example.shoppingapp.domain.usecase.home.HomeUseCases
import com.example.shoppingapp.domain.usecase.payment.GetPaymentSummaryForItemsUseCase
import com.example.shoppingapp.domain.usecase.payment.GetPaymentSummaryUseCase
import com.example.shoppingapp.domain.usecase.payment.PayUseCase
import com.example.shoppingapp.domain.usecase.payment.PaymentUseCase
import com.example.shoppingapp.domain.usecase.payment.SetPaymentMethodUseCase
import com.example.shoppingapp.domain.usecase.payment.SetShippingOptionUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    factory {
        GetBannersUseCase(
            homeRepository = get()
        )
    }
    factory {
        GetCategoryUseCase(
            categoryRepository = get()
        )
    }
    factory {
        GetItemsUseCase(
            get()
        )
    }
    factory {
        HomeUseCases(
            getBannersUseCase = get(),
            getCategoryUseCase = get(),
            getItemsUseCase = get(),
            addItemToCartUseCase = get()
        )
    }

    factory {
        GetItemsByCategoryUseCase(
            categoryRepository = get()
        )
    }

    factory {
        AddItemToCartUseCase(
            get()
        )
    }
    factory {
        GetItemDetailUseCase(
            get()
        )
    }
    factory {
        AddItemToFavUseCase(
            get()
        )
    }
    factory {
        RemoveItemFromFavUseCase(
            get()
        )
    }
    factory {
        GetFavItemByIDUseCase(
            get()
        )
    }
    factory {
        DetailScreenUseCase(
            getItemDetailUseCase = get(),
            addItemToCartUseCase = get(),
            addItemToFavUseCase = get(),
            removeItemFromFavUseCase = get(),
            getFavItemByIDUseCase = get()
        )
    }

    factory {
        GetCartItemUseCase(
            get()
        )
    }
    factory {
        IncreaseCartItemQtyUseCase(
            get()
        )
    }
    factory {
        DecreaseCartItemQtyUseCase(
            get()
        )
    }
    factory {
        RemoveCartItemUseCase(
            get()
        )
    }
    factory {
        CartScreenUseCase(
            getCartItemUseCase = get(),
            increaseCartItemQtyUseCase = get(),
            decreaseCartItemQtyUseCase = get(),
            removeCartItemUseCase = get()
        )
    }

    factory {
        GetFavItemsUseCase(
            get()
        )
    }
    factory {
        FavScreenUseCase(
            getFavItemsUseCase = get()
        )
    }


    factory {
        GetShippingAddressUseCase(
            get()
        )
    }
    factory {
        SaveShippingAddressUseCase(
            get()
        )
    }
    factory {
        AddressScreenUseCase(
            getShippingAddressUseCase = get(),
            saveShippingAddressUseCase = get()
        )
    }


    factory{
        GetPaymentSummaryUseCase(
            get()
        )
    }
    factory {
        GetPaymentSummaryForItemsUseCase(
            get()
        )
    }
    factory {
        SetShippingOptionUseCase(
            get()
        )
    }
    factory {
        SetPaymentMethodUseCase(
            get()
        )
    }
    factory {
        PayUseCase(
            get()
        )
    }
    factory {

    }
    factory {
        PaymentUseCase(
            get(),get(),get(),get(), get(), get()
        )
    }

}