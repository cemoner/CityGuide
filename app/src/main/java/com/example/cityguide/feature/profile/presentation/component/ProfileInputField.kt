package com.example.cityguide.feature.profile.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguide.ui.theme.LightSubTextColor

@Composable
fun ProfileInputField(
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit,
    validation:(String) -> Boolean
){

    var isInputValid by remember { mutableStateOf(validation(value)) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
            isInputValid = validation(it)
                        },
        enabled = true,
        singleLine = true,
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.bodyMedium,
                color = LightSubTextColor
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        trailingIcon = {
            if (isInputValid) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Valid Input",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            else {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Invalid Input",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
         }
        ,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            focusedTextColor =  MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
    )
}