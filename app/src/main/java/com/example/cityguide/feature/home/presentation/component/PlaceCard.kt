package com.example.cityguide.feature.home.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import coil3.size.Size
import com.example.cityguide.R
import com.example.cityguide.feature.home.domain.model.Place
import com.example.cityguide.feature.home.util.getPhotoUrl

@Composable
fun PlaceCard(place: Place?) {
    if (place == null || place.name.isBlank() || place.address.isBlank() || place.photoReference == null || place.rating == null) {
        Log.d("PlaceCard", "Skipping empty place: $place")
        return
    }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            place.photoReference.let { photoRef ->
                val imageUrl = getPhotoUrl(photoRef)
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl.ifBlank { null })
                        .crossfade(true)
                        .allowHardware(false)
                        .size(Size.ORIGINAL)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .fallback(R.drawable.placeholder_image)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "Image of ${place.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.surface,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
                Text(
                    text = place.address,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.surface,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                place.rating.let { rating ->
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = String.format("%.1f", rating),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                shadow = Shadow(
                                    color = MaterialTheme.colorScheme.surface,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
