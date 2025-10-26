package com.example.shoppingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.presentation.login.LoginScreen
import com.example.shoppingapp.presentation.register.RegisterScreen

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            route = startDestination,
            startDestination = NavRoute.LoginScreen.route
        ){
            composable(NavRoute.LoginScreen.route){
                LoginScreen(
                    navigateToRegister = { navController.navigate(NavRoute.RegisterScreen.route) },
                    navigateToHome = {
                        navController.navigate(NavRoute.MainNavigation.route) {
                            popUpTo(NavRoute.AppStartNavigation.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(NavRoute.RegisterScreen.route){
                RegisterScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToHome = {
                        navController.navigate(NavRoute.MainNavigation.route) {
                            popUpTo(NavRoute.AppStartNavigation.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        navigation(
            route = NavRoute.MainNavigation.route,
            startDestination = NavRoute.MenuNavigation.route
        ){
            composable(NavRoute.MenuNavigation.route){
                BottomNavigation()
            }
        }

    }
}