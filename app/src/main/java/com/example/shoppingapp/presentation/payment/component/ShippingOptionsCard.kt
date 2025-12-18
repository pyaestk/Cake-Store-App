package com.example.shoppingapp.presentation.payment.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.domain.model.ShippingOption

@Composable
fun ShippingOptionsCard(
    selected: ShippingOption,
    onSelected: (ShippingOption) -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    Card(
        modifier = Modifier.fillMaxWidth().selectableGroup(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            ShippingRow(
                title = "Standard",
                days = "5-7 days",
                price = "FREE",
                isSelected = selected == ShippingOption.Standard,
                onClick = { onSelected(ShippingOption.Standard) }
            )

            Spacer(Modifier.height(8.dp))

            ShippingRow(
                title = "Express",
                days = "1-2 days",
                price = "$12,00",
                isSelected = selected == ShippingOption.Express,
                onClick = { onSelected(ShippingOption.Express) }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Delivered on or before Thursday, 23 April 2020",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ShippingRow(
    title: String,
    days: String,
    price: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val rowBg = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.10f) else Color.Transparent
    val shape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(rowBg)
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )

        Spacer(Modifier.width(8.dp))

        Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

        Spacer(Modifier.width(10.dp))

        AssistChip(
            onClick = onClick,
            label = { Text(days, style = MaterialTheme.typography.labelSmall) }
        )

        Spacer(Modifier.weight(1f))

        Text(price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}