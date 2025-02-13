package com.example.cityguide.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cityguide.feature.home.domain.model.PlaceDetail
import com.example.cityguide.feature.home.util.launchNavigation

@Composable
fun DirectionsSection(place: PlaceDetail) {
    val context = LocalContext.current
    val staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=${place.latitude},${place.longitude}" +
            "&zoom=15" +
            "&size=600x300" +
            "&markers=color:red%7C${place.latitude},${place.longitude}" +
            "&key=AIzaSyDVMjIHxNx35GAiKxZgZ1numDIv8UGZXOs"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                launchNavigation(context, place.latitude, place.longitude)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Text(
                text = "Get Directions",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            AsyncImage(
                model = staticMapUrl,
                contentDescription = "Map snapshot for directions",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            )
        }
    }
}