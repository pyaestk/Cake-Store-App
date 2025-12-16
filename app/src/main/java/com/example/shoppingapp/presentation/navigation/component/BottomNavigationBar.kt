package com.example.shoppingapp.presentation.navigation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.presentation.navigation.BottomNavItems

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItems>,
    onClick: (BottomNavItems) -> Unit,
    selectedItem: Int,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 10.dp,
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onClick(item) },
                alwaysShowLabel = true,

                icon = {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },

                label = {
                    androidx.compose.material3.Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelSmall
                    )
                },

                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,

                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f),

                    selectedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
    }
}




