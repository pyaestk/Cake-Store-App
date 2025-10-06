package com.example.shoppingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.shoppingapp.presentation.navigation.NavGraph
import com.example.shoppingapp.presentation.navigation.NavRoute
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShoppingAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    NavGraph(
                        startDestination = NavRoute.AppStartNavigation.route
                    )
                }
            }
        }
    }
}