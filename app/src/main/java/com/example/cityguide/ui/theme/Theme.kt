package com.example.cityguide.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryBlue,

    background = DarkBackgroundColor,
    surface = DarkBackgroundColor,

    // Text and content colors
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = DarkTextColor,
    onSurface = DarkTextColor,

    // Subtext and action colors
    onTertiary = DarkSubTextColor,
    onError = Color.Black
)
private val LightColorScheme = lightColorScheme(
    primary = MyPrimaryBlue,

    // Background and surface
    background = LightBackgroundColor,
    surface = LightBackgroundColor,

    // Text and content colors
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = LightTextColor,
    onSurface = LightTextColor,

    // Subtext and action colors
    onTertiary = LightSubTextColor,
    onError = Color.White,
)

@Composable
fun CityGuideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}