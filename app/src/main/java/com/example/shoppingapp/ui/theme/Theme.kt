package com.example.shoppingapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // Brand
    primary = Color(0xFFCC9C68),       // your brown (lighter for dark mode)
    onPrimary = Color(0xFF2A1A0E),
    secondary = Color(0xFF864912),
    onSecondary = Color(0xFFFFF3E8),

    // Surfaces (these are what make dark mode look “clean”)
    background = Color(0xFF0F1113),
    onBackground = Color(0xFFEDE7E0),
    surface = Color(0xFF14171A),
    onSurface = Color(0xFFEDE7E0),

    // Cards / textfields / containers
    surfaceVariant = Color(0xFF1C2126),
    onSurfaceVariant = Color(0xFFCAD0D6),

    // Borders / dividers
    outline = Color(0xFF3A3F46)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF864912),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFCC9C68),
    onSecondary = Color(0xFF2A1A0E),

    background = Color(0xFFF7F5F2),
    onBackground = Color(0xFF1B1B1B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1B),

    surfaceVariant = Color(0xFFEFE9E1),
    onSurfaceVariant = Color(0xFF4A4540),

    outline = Color(0xFFD7CEC2)
)


@Composable
fun ShoppingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}