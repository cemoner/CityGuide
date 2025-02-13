package com.example.cityguide.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityguide.feature.home.presentation.viewmodel.CityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCity(
    viewModel: CityViewModel = viewModel()
) {
    var text by remember { mutableStateOf("") }
    val suggestions by viewModel.suggestions.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                if (newText.isNotBlank() && newText.length > 1) {
                    viewModel.fetchCitySuggestions(newText)
                }
                expanded = newText.isNotBlank()
            },
            modifier = Modifier
                .menuAnchor()
                .width(125.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.surface)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    expanded = isFocused && suggestions.isNotEmpty()
                },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false),
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface)
        ) {
            if (text.isNotBlank() && suggestions.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No result") },
                    onClick = { expanded = false }
                )
            } else {
                suggestions.forEach { city ->
                    DropdownMenuItem(
                        text = { Text("${city.name}, ${city.country}") },
                        onClick = {
                            viewModel.setCityAndCountry(city.name,city.country)
                            text = "${city.name}, ${city.country}"
                            expanded = false
                        }
                    )
                }
            }
        }

    }
}
