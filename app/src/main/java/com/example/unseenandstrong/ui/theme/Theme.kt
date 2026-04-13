package com.example.unseenandstrong.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val FlareDayColorScheme = lightColorScheme(
    primary = LavenderPurple,
    secondary = SoftBlushPink,
    tertiary = SoftBlushPink,
    background = NightLavender,
    surface = NightLavender,
    onPrimary = PaleCloudWhite,
    onSecondary = PaleCloudWhite,
    onTertiary = PaleCloudWhite,
    onBackground = PaleCloudWhite,
    onSurface = PaleCloudWhite
)

private val NormalColorScheme = lightColorScheme(
    primary = SoftBlushPink,
    secondary = LavenderPurple,
    tertiary = LavenderPurple,
    background = SoftCloudGrey,
    surface = SoftCloudGrey,
    onPrimary = DeepFogGrey,
    onSecondary = DeepFogGrey,
    onTertiary = DeepFogGrey,
    onBackground = DeepFogGrey,
    onSurface = DeepFogGrey
)

@Composable
fun UnseenAndStrongTheme(
    isFlareDay: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isFlareDay) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isFlareDay -> FlareDayColorScheme
        else -> NormalColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}