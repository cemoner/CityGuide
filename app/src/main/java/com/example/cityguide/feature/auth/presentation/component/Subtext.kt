package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.example.cityguide.ui.theme.LightSubTextColor

@Composable
fun Subtext(text:String){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = LightSubTextColor,
        textAlign = TextAlign.Center
    )
}