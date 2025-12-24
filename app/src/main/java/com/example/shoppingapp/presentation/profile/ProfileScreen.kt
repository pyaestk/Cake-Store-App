package com.example.shoppingapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.presentation.profile.component.OrderInfoBoxes
import com.example.shoppingapp.presentation.profile.component.SettingItems
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navigateToOrder: (() -> Unit)? = null,
    navigateToAddress: (() -> Unit)? = null,
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            title = { Text("Log out?") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    onClick = {
                        openAlertDialog.value = false
                        viewModel.onEvent(ProfileScreenEvent.Logout)
                    }
                ) {
                    Text("Log out")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openAlertDialog.value = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Profile",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /*AsyncImage(
                model = painterResource(R.drawable.user_profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(
                        CircleShape
                    )
            )*/
            Image(
                painter = painterResource(R.drawable.user_profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Dylan",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "dylanseven@gmail.com",
                    modifier = Modifier.padding(top = 5.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OrderInfoBoxes(
                count = 39,
                title = "Total Orders",
                modifier = Modifier.weight(1f),
            )

            OrderInfoBoxes(
                count = 2,
                title = "Active Orders",
                modifier = Modifier.weight(1f),
            )

            OrderInfoBoxes(
                count = 3,
                title = "Favorite Items",
                modifier = Modifier.weight(1f),
            )
        }

        Text(
            text = "General",
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
//        SettingItems(
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .padding(top = 16.dp),
//            title = "Edit Profile",
//            icon = R.drawable.ic_edit
//        )
        SettingItems(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clickable {
                    navigateToOrder?.invoke()
                },
            title = "Order History",
            icon = R.drawable.ic_history
        )
        SettingItems(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clickable {
                    navigateToAddress?.invoke()
                },
            title = "Shipping Address",
            icon = R.drawable.ic_location
        )
//        SettingItems(
//            modifier = Modifier
//                .padding(horizontal = 16.dp)
//                .padding(top = 16.dp),
//            title = "Change Password",
//            icon = R.drawable.ic_password
//        )
        SettingItems(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clickable {
                    openAlertDialog.value = true
                },
            title = "Log Out",
            icon = R.drawable.ic_logout
        )

    }
}


@Composable
@Preview(showBackground = true)
fun PreviewProfileScreen() {
    ShoppingAppTheme {
        ProfileScreen(
            modifier = Modifier,
            paddingValues = PaddingValues(vertical = 16.dp)
        )
    }
}