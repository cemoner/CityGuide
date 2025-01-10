package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cityguide.ui.theme.LightSubTextColor

@Composable
fun AuthInputField(
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val icon = if (passwordVisible) {
        Icons.Outlined.Visibility
    } else {
        Icons.Outlined.VisibilityOff
    }

    val visualTransformation = if (isPassword && passwordVisible) {
        VisualTransformation.None
    } else {
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        enabled = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = if (isPassword && !passwordVisible)LightSubTextColor else MaterialTheme.colorScheme.onSurface,
        ),
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.bodyMedium,
                color = LightSubTextColor
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
            imeAction =  if (isPassword) ImeAction.Done else ImeAction.Next
        ),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = LightSubTextColor
                    )
                }
            }
        },
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
        shape = RoundedCornerShape(14.dp)
    )
}



