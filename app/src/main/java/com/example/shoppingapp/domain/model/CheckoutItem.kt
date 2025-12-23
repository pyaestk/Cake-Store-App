package com.example.shoppingapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CheckoutItem(
    val itemId: Int,
    val qty: Int,
    val size: String = ""
) : Parcelable
