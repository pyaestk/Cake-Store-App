package com.example.shoppingapp.presentation.navigation


sealed class NavRoute(
    val route: String
){
    object AppStartNavigation: NavRoute("appStartNavigation")
    object LoginScreen: NavRoute("LoginScreen")
    object RegisterScreen: NavRoute("RegisterScreen")

    object MainNavigation: NavRoute("MainNavigation")
    object MenuNavigation: NavRoute("MenuNavigation")
    object HomeScreen: NavRoute("home")
    object CartScreen: NavRoute("cart")
    object FavoriteScreen: NavRoute("favorite")
    object ProfileScreen: NavRoute("profile")

    object DetailScreen : NavRoute("detail/{itemId}") {
        fun createRoute(itemId: Int) = "detail/${itemId}"
    }
    object CategoryScreen: NavRoute("category")
}
