package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguide.ui.theme.LightSubTextColor

@Composable
fun AuthTextLink(
    normalText: String?,
    clickableText: String?,
    onClick: () -> Unit
) {

    if(normalText != null){
        Text(
            text = normalText,
            style = MaterialTheme.typography.bodyMedium,
            color = LightSubTextColor
        )
    }

    if(normalText != null && clickableText != null){
        Spacer(modifier = Modifier.width(4.dp))
    }

    if(clickableText != null){
        Text(
            text = clickableText,
            modifier = Modifier.clickable(onClick = onClick),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}