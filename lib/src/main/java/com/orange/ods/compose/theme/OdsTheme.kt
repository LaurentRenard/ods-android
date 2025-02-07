/*
 * Software Name: Orange Design System
 * SPDX-FileCopyrightText: Copyright (c) Orange SA
 * SPDX-License-Identifier: MIT
 *
 * This software is distributed under the MIT license,
 * the text of which is available at https://opensource.org/license/MIT/
 * or see the "LICENSE" file for more details.
 *
 * Software description: Android library of reusable graphical components 
 */

package com.orange.ods.compose.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.orange.ods.theme.OdsComponentsConfiguration
import com.orange.ods.theme.OdsThemeConfigurationContract
import com.orange.ods.theme.colors.OdsColors
import com.orange.ods.theme.typography.OdsTypography

private fun odsThemeError(message: Any): Nothing = error("OdsTheme not found. $message")

internal val LocalColors = staticCompositionLocalOf<OdsColors> { odsThemeError("LocalColors CompositionLocal not present") }
private val LocalLightThemeColors = compositionLocalOf<OdsColors> { odsThemeError("LocalLightThemeColors CompositionLocal not present") }
private val LocalDarkThemeColors = compositionLocalOf<OdsColors> { odsThemeError("LocalDarkThemeColors CompositionLocal not present") }

private val LocalShapes = staticCompositionLocalOf { Shapes() }
private val LocalTypography = staticCompositionLocalOf { OdsTypography() }
private val LocalComponentsConfiguration = staticCompositionLocalOf { OdsComponentsConfiguration() }

private val LocalDarkThemeEnabled = staticCompositionLocalOf<Boolean> { odsThemeError("LocalDarkThemeEnabled CompositionLocal not present.") }

object OdsTheme {

    val colors: OdsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val lightThemeColors: OdsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalLightThemeColors.current

    val darkThemeColors: OdsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDarkThemeColors.current

    val typography: OdsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val componentsConfiguration: OdsComponentsConfiguration
        @Composable
        @ReadOnlyComposable
        get() = LocalComponentsConfiguration.current
}

/**
 * ODS theme is the theme to apply to your screens in an Orange Jetpack Compose application.
 *
 * @param themeConfiguration The configuration of the OdsTheme: colors, typography...
 * @param darkThemeEnabled Indicates whether the dark theme is enabled or not.
 * @param content The content of the theme.
 */
@Composable
fun OdsTheme(
    themeConfiguration: OdsThemeConfigurationContract,
    darkThemeEnabled: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkThemeEnabled) themeConfiguration.colors.darkColors else themeConfiguration.colors.lightColors

    CompositionLocalProvider(
        LocalDarkThemeEnabled provides darkThemeEnabled,
        LocalRippleTheme provides OdsRippleTheme,
        LocalColors provides colors,
        LocalLightThemeColors provides themeConfiguration.colors.lightColors,
        LocalDarkThemeColors provides themeConfiguration.colors.darkColors,
        LocalTypography provides themeConfiguration.typography,
        LocalShapes provides themeConfiguration.shapes,
        LocalComponentsConfiguration provides themeConfiguration.componentsConfiguration
    ) {
        MaterialTheme(
            colors = colors.material,
            typography = themeConfiguration.typography.materialTypography
        ) {
            SystemBarsColorSideEffect()
            content()
        }
    }
}

/**
 * Tweak type of the current [OdsTheme] which can be pass to [OdsThemeTweak] composable:
 *   - Inverted set theme in dark when app is in light or in light when app is in dark
 *   - ForceDark and ForceLight force the theme to be in dark or in light
 */
enum class OdsThemeTweakType {
    Inverted, ForceDark, ForceLight
}

/**
 * Tweaks the current ODS theme according the given [tweakType] and display given [content] according that tweak.
 * Note: This composable is directly related to [OdsTheme] and MUST be used inside it.
 */
@Composable
fun OdsThemeTweak(tweakType: OdsThemeTweakType, content: @Composable () -> Unit) {
    val tweakedToDark = when (tweakType) {
        OdsThemeTweakType.Inverted -> !LocalDarkThemeEnabled.current
        OdsThemeTweakType.ForceDark -> true
        OdsThemeTweakType.ForceLight -> false
    }
    val colors: OdsColors
    val rippleTheme: RippleTheme
    if (tweakedToDark) {
        colors = OdsTheme.darkThemeColors
        rippleTheme = OdsDarkRippleTheme
    } else {
        colors = OdsTheme.lightThemeColors
        rippleTheme = OdsLightRippleTheme
    }

    CompositionLocalProvider(
        LocalDarkThemeEnabled provides tweakedToDark,
        LocalRippleTheme provides rippleTheme,
        LocalColors provides colors,
        LocalContentColor provides colors.onSurface
    ) {
        MaterialTheme(
            colors = colors.material,
            typography = LocalTypography.current.materialTypography
        ) {
            content()
        }
    }
}

/**
 * Manages the system bars appearance based on theme configuration.
 */
@Composable
private fun SystemBarsColorSideEffect() {
    val systemBarsBackground = OdsTheme.colors.components.statusBar.background
    val isAppearanceLightBars = OdsTheme.colors.components.statusBar.isAppearanceLight
    val activity = LocalContext.current as? ComponentActivity
    activity?.window?.let { window ->
        val view = LocalView.current
        SideEffect {
            window.statusBarColor = systemBarsBackground.toArgb()
            window.navigationBarColor = systemBarsBackground.toArgb()
            with(WindowCompat.getInsetsController(window, view)) {
                isAppearanceLightStatusBars = isAppearanceLightBars
                isAppearanceLightNavigationBars = isAppearanceLightBars
            }
        }
    }
}
