package com.example.shoppingapp.presentation.home

import com.example.shoppingapp.domain.model.BannerModel
import com.example.shoppingapp.domain.model.CategoryModel
import com.example.shoppingapp.domain.model.ItemModel

data class HomeScreenState(
    val banners: List<BannerModel> = emptyList(),
    val categories: List<CategoryModel> = emptyList(),
    val items: List<ItemModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)