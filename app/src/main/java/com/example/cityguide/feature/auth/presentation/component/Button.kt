package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cityguide.ui.theme.LightBackgroundColor

@Composable
fun AuthButton(label:String,onClick:() -> Unit){
    Button(onClick = onClick,modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)).padding(4.dp))
    {
        Text(text = label, style = MaterialTheme.typography.titleSmall, color = LightBackgroundColor)
    }
}

@Composable
fun SocialMediaButton(platform:String,onClick:(String) -> Unit,id:Int){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.background(MaterialTheme.colorScheme.background, shape = CircleShape).padding(horizontal = 2.dp)){
        IconButton(
            onClick = { onClick(platform) },
            modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(50)).padding(2.dp)
        ) {
            val iconPainter: Painter = when (platform) {
                "google" -> painterResource(id = id)
                "facebook" -> painterResource(id = id)
                "twitter" -> painterResource(id = id)
                else -> throw IllegalArgumentException("Unknown platform: $platform")
            }

            Image(
                painter = iconPainter,
                contentDescription = "$platform button",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}