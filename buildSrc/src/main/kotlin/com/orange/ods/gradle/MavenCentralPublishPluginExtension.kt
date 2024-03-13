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

package com.orange.ods.gradle

abstract class MavenCentralPublishPluginExtension {

    companion object {
        const val NAME = "mavenCentralPublish"
        const val VARIANT = "prodRelease"
    }

    var artifactId: String? = null

    var enabled: Boolean = true
}
