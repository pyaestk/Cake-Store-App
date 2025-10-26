package com.example.shoppingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shoppingapp.presentation.navigation.NavGraph
import com.example.shoppingapp.presentation.navigation.NavRoute
import com.example.shoppingapp.presentation.splash.SplashScreen
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShoppingAppTheme {
                val mainViewModel: MainViewModel = koinViewModel()
                val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()

                when (isLoggedIn) {
                    null -> {
                        SplashScreen()
                    }
                    true -> {
                        NavGraph(startDestination = NavRoute.MainNavigation.route)
                    }
                    false -> {
                        NavGraph(startDestination = NavRoute.AppStartNavigation.route)
                    }
                }
            }
        }
    }
}