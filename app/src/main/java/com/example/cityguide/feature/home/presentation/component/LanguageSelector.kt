package com.example.cityguide.feature.home.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cityguide.R
import com.example.cityguide.main.util.LanguageChangeHelper

@Composable
fun LanguageSelector() {
    var isDropdownOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val applicationContext = context.applicationContext

    val languageChangeHelper by lazy {
        LanguageChangeHelper()
    }

    var selectedLanguage by remember { mutableStateOf(languageChangeHelper.getLanguageCode(applicationContext)) }

    val languageFlags = mapOf(
        "en" to R.drawable.flag_english,
        "tr" to R.drawable.flag_turkish,
        "de" to R.drawable.flag_german
    )
    val languageNames = mapOf(
        "en" to "English",
        "tr" to "Türkçe",
        "de" to "Deutsch"
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.clickable { isDropdownOpen = !isDropdownOpen }, verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { isDropdownOpen = !isDropdownOpen }) {
                Image(
                    painter = painterResource(id = languageFlags[selectedLanguage]!!),
                    contentDescription = "Current Language",
                    modifier = Modifier.size(28.dp)
                )
            }
            Text(languageNames[selectedLanguage] ?: "Unknown", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground)
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                contentDescription = ""
            )
        }
        Box(
            modifier = Modifier
                .offset(y = (32.dp),x = 60.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(6.dp)
                .align(Alignment.TopCenter)
        ) {
            DropdownMenu(
                expanded = isDropdownOpen,
                onDismissRequest = { isDropdownOpen = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                languageFlags.forEach { (languageCode, flagRes) ->
                    DropdownMenuItem(
                        onClick = {
                            selectedLanguage = languageCode
                            languageChangeHelper.changeLanguage(applicationContext,languageCode)
                            isDropdownOpen = false
                        },
                        enabled = if (selectedLanguage == languageCode) false else true,
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = flagRes),
                                    contentDescription = "Language Flag",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(languageNames[languageCode] ?: "Unknown", style = MaterialTheme.typography.bodyMedium,
                                    color = if (selectedLanguage == languageCode) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onBackground)
                            }
                        }
                    )
                }
            }
        }
    }
}