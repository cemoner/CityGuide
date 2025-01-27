package com.example.cityguide.feature.home.presentation.component

import ProfileImageUrlSingleton
import ProfileNameSingleton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cityguide.navigation.model.Destination
import com.example.cityguide.navigation.navigator.AppNavigator

@Composable
fun Title(appNavigator: AppNavigator) {
    val profileImageUrl by ProfileImageUrlSingleton.profileImageUrl.collectAsState()
    val name by ProfileNameSingleton.profileName.collectAsState(initial = "")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = CircleShape)
            .padding(horizontal = 2.dp)
    ) {
        IconButton(
            onClick = { appNavigator.tryNavigateTo(Destination.Profile()) },
            modifier = Modifier
                .size(48.dp)
                .background(color = Color.Transparent, RoundedCornerShape(50))
        ) {
            if (profileImageUrl.isNotEmpty()) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surface)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Default Profile Icon",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}
