package com.example.shoppingapp.data.util

import PayResultResponse
import PaymentItemResponse
import PaymentSummaryResponse
import com.example.shoppingapp.data.model.response.AddressResponse
import com.example.shoppingapp.data.model.response.BannerResponse
import com.example.shoppingapp.data.model.response.CartItemResponse
import com.example.shoppingapp.data.model.response.CategoryResponse
import com.example.shoppingapp.data.model.response.ItemResponse
import com.example.shoppingapp.data.model.response.UserResponse
import com.example.shoppingapp.domain.model.AddressModel
import com.example.shoppingapp.domain.model.BannerModel
import com.example.shoppingapp.domain.model.CartItemModel
import com.example.shoppingapp.domain.model.CategoryModel
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.domain.model.PayResultModel
import com.example.shoppingapp.domain.model.PaymentItemModel
import com.example.shoppingapp.domain.model.PaymentSummaryModel
import com.example.shoppingapp.domain.model.UserModel


fun BannerResponse.toModel() = BannerModel(
    url = url
)

fun CategoryResponse.toModel() = CategoryModel(
    id = id,
    picUrl = picUrl,
    title = title
)

fun ItemResponse.toModel(): ItemModel {
    return ItemModel(
        id  = id,
        categoryId = this.categoryId,
        description = this.description,
        picUrl = this.picUrl,
        price = this.price,
        rating = this.rating,
        sellerName = this.sellerName,
        sellerPic = this.sellerPic,
        sellerTell = this.sellerTell,
        showRecommended = this.showRecommended,
        size = this.size,
        title = this.title
    )
}

fun CartItemResponse.toModel() = CartItemModel(
    itemId = itemId,
    quantity = quantity,
    size = size,
    title = title,
    price = price,
    picUrl = picUrl
)

fun UserResponse.toModel() = UserModel(
    id = id,
    name = name,
    email = email,
    password = password
)

fun AddressResponse.toModel() = AddressModel(
    name = name,
    addressNum = addressNum,
    city = city,
    country = country,
    zipCode = zipCode,
    phoneNumber = phoneNumber
)

fun PaymentItemResponse.toModel() = PaymentItemModel(
    id = id,
    title = title,
    subtitle = subtitle,
    imageUrl = imageUrl,
    qty = qty,
    price = price,
)

fun PaymentSummaryResponse.toModel() = PaymentSummaryModel (
    shippingAddress = shippingAddress,
    items = items.map { it.toModel() },
    selectedShipping = selectedShipping,
    selectedPaymentMethod = selectedPaymentMethod,
    itemsTotal = itemsTotal,
    shippingFee = shippingFee,
    grandTotal = grandTotal
)

fun PayResultResponse.toModel() = PayResultModel(
    orderId = orderId,
    status = status
)