package com.shilpakala.showcase.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = GoldPrimary,
    onPrimary = LightSurface,
    primaryContainer = GoldSubtle,
    onPrimaryContainer = GoldDark,
    secondary = WarmBrown,
    onSecondary = LightSurface,
    secondaryContainer = GoldSubtle,
    onSecondaryContainer = WarmBrown,
    tertiary = SoftTerracotta,
    onTertiary = LightSurface,
    background = OffWhite,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
    outlineVariant = ShimmerBase,
    error = ErrorRed,
    onError = LightSurface,
    errorContainer = ErrorRedLight,
    onErrorContainer = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = GoldLight,
    onPrimary = DeepCharcoal,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldSubtle,
    secondary = WarmBrownLight,
    onSecondary = DeepCharcoal,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = WarmBrownLight,
    tertiary = SoftTerracotta,
    onTertiary = DeepCharcoal,
    background = DeepCharcoal,
    onBackground = TextPrimaryDark,
    surface = DarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark,
    outlineVariant = ShimmerBaseDark,
    error = ErrorRedLight,
    onError = DeepCharcoal,
    errorContainer = ErrorRed,
    onErrorContainer = ErrorRedLight
)

@Composable
fun ShilpaKalaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ShilpaKalaTypography,
        shapes = ShilpaKalaShapes,
        content = content
    )
}
