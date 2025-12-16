package com.example.shoppingapp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.model.CategoryModel
import com.example.shoppingapp.presentation.address.AddressScreen
import com.example.shoppingapp.presentation.cart.CartScreen
import com.example.shoppingapp.presentation.category.CategoryScreen
import com.example.shoppingapp.presentation.detail.DetailScreen
import com.example.shoppingapp.presentation.fav.FavScreen
import com.example.shoppingapp.presentation.home.HomeScreen
import com.example.shoppingapp.presentation.navigation.component.BottomNavigationBar
import com.example.shoppingapp.presentation.payment.component.PaymentScreen
import com.example.shoppingapp.presentation.payment.mockPaymentUiState
import com.example.shoppingapp.presentation.profile.ProfileScreen

@Composable
fun BottomNavigation() {

    val navController = rememberNavController()

    val showBottomNav = remember {
        mutableStateOf(true)
    }

    val items = remember {
        listOf(
            BottomNavItems.Home,
            BottomNavItems.Favorite,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )
    }
    val backstackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    val route = backstackState?.destination?.route

    selectedItem = when (route) {
        NavRoute.HomeScreen.route -> 0
        NavRoute.FavoriteScreen.route -> 1
        NavRoute.CartScreen.route -> 2
        NavRoute.ProfileScreen.route -> 3
        else -> selectedItem
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    items = items,
                    onClick = { item ->
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { startRoute ->
                                popUpTo(startRoute) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = NavRoute.HomeScreen.route,
            modifier = Modifier,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() },
        ) {

            composable(
                NavRoute.HomeScreen.route,
            ) {
                HomeScreen(
                    paddingValues = paddingValues,
                    navigateToDetail = { item ->
                        navigateToDetails(
                            navController = navController,
                            itemId = item.id,
                        )
                    },
                    navigateToCategory = { category ->
                        navigateToCategory(
                            navController = navController,
                            categoryModel = category
                        )
                    }
                )
                showBottomNav.value = true
            }
            composable(
                NavRoute.CartScreen.route,
            ) {
                CartScreen(
                    paddingValues = paddingValues,
                    onItemClick = { item ->
                        navigateToDetails(
                            navController = navController,
                            itemId = item.itemId,
                        )
                    },
                    navigateToPayment = {
                        navController.navigate(NavRoute.PaymentScreen.route)
                    }
                )
                showBottomNav.value = true
            }

            composable(
                NavRoute.FavoriteScreen.route,
            ) {
                FavScreen(
                    paddingValues = paddingValues,
                    onItemClick = { item ->
                        navigateToDetails(
                            navController = navController,
                            itemId = item.id,
                        )
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
                showBottomNav.value = true
            }
            composable(
                NavRoute.ProfileScreen.route,
                ) {
                ProfileScreen(
                    paddingValues = paddingValues,
                    navigateToAppStart = {
                        navController.navigate(NavRoute.AppStartNavigation.route) {
                            popUpTo(NavRoute.MainNavigation.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToAddress = {
                        navController.navigate(NavRoute.AddressScreen.route)
                    }
                )
                showBottomNav.value = true
            }
            composable(
                NavRoute.DetailScreen.route, // Use the route directly from NavRoute
                enterTransition = { slideInHorizontally { it } + fadeIn() },
                exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                popExitTransition = { slideOutHorizontally { it } + fadeOut() },
                arguments = listOf(navArgument("itemId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getInt("itemId") ?: return@composable

                DetailScreen(
                    itemId = itemId,
                    onBackClick = { navController.navigateUp() }
                )
                showBottomNav.value = false
            }
            composable(
                NavRoute.CategoryScreen.route,
                enterTransition = { slideInHorizontally { it } + fadeIn() },
                exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                popExitTransition = { slideOutHorizontally { it } + fadeOut() }
            ) {
                navController.previousBackStackEntry?.savedStateHandle?.get<CategoryModel>("category")
                    ?.let { category ->
                        CategoryScreen(
                            category = category,
                            onBackClick = {
                                navController.navigateUp()
                            },
                            onItemClick = { item ->
                                navigateToDetails(
                                    navController = navController,
                                    itemId = item.id,
                                )
                            }

                        )
                    }
                showBottomNav.value = false
            }
            composable(
                NavRoute.AddressScreen.route,
                enterTransition = { slideInHorizontally { it } + fadeIn() },
                exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                popExitTransition = { slideOutHorizontally { it } + fadeOut() }
            ) {
                AddressScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
                showBottomNav.value = false
            }
            composable(
                NavRoute.PaymentScreen.route,
                enterTransition = { slideInHorizontally { it } + fadeIn() },
                exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
                popExitTransition = { slideOutHorizontally { it } + fadeOut() }
            ) {
                PaymentScreen(
                    state = mockPaymentUiState,
                    onBackClick = {
                        navController.navigateUp()
                    },
                    onEditAddress = {},
                    onEditContact = {},
                    onShippingChange = {},
                    onPaymentMethodChange= {},
                    onPayClick = {}
                )
                showBottomNav.value = false
            }
        }
    }


}

private fun navigateToDetails(navController: NavController, itemId: Int) {
    navController.navigate(NavRoute.DetailScreen.createRoute(itemId))
}


private fun navigateToCategory(navController: NavController, categoryModel: CategoryModel) {
    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "category",
        value = categoryModel
    )
    navController.navigate(NavRoute.CategoryScreen.route)
}

sealed class BottomNavItems(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavItems(NavRoute.HomeScreen.route, "Home", icon = R.drawable.btn_1)
    object Cart : BottomNavItems(NavRoute.CartScreen.route, "Cart", icon = R.drawable.btn_2)

    object Favorite : BottomNavItems(NavRoute.FavoriteScreen.route, "Favorite", icon = R.drawable.btn_3)
    object Profile :
        BottomNavItems(NavRoute.ProfileScreen.route, "Profile", icon = R.drawable.btn_5)
}
