package com.example.shoppingapp.presentation.navigation.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.navigation.BottomNavItems

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItems>,
    onClick: (BottomNavItems) -> Unit,
    selectedItem: Int,
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorResource(R.color.midBrown),
        tonalElevation = 10.dp,
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onClick(item) },
                alwaysShowLabel = true,
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            if (index == selectedItem) Color.White
                            else Color.LightGray.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    androidx.compose.material3.Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (index == selectedItem) Color.White else Color.LightGray.copy(alpha = 0.7f)
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.1f),
                    unselectedTextColor = Color.White.copy(alpha = 0.1f),
                    selectedIndicatorColor = Color.Transparent
                ),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}



