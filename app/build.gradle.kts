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
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.orange.ods.gradle.Environment
import com.orange.ods.gradle.findTypedProperty

plugins {
    id("firebase")
    id(libs.plugins.android.application.get().pluginId) // https://github.com/gradle/gradle/issues/20084#issuecomment-1060822638
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId) // This must be the last statement in the plugins {} to avoid "options not recognized" warning
}

android {
    namespace = "com.orange.ods.app"

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        val versionCodeProperty = project.findTypedProperty<String>("versionCode")
        versionCode = versionCodeProperty?.toInt() ?: 11
        versionName = version.toString()
        val versionNameSuffixProperty = project.findTypedProperty<String>("versionNameSuffix")
        versionNameSuffix = versionNameSuffixProperty

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Set AppsPlus API key
        buildConfigField("String", "APPS_PLUS_API_KEY", "\"${gradleLocalProperties(rootDir, providers).getProperty("APPS_PLUS_API_KEY")}\"")
    }

    val signingConfigName = "signingConfig"
    val storeFilePath = Environment.getVariablesOrNull("SIGNING_STORE_FILE_PATH").first()
    val storeFile = file(storeFilePath ?: "./app.keystore").takeIf { it.exists() }
    if (storeFile != null) {
        signingConfigs {
            create(signingConfigName) {
                val (storePassword, keyAlias, keyPassword) = Environment.getVariablesOrNull(
                    "SIGNING_STORE_PASSWORD",
                    "SIGNING_KEY_ALIAS",
                    "SIGNING_KEY_PASSWORD"
                )
                this.storeFile = storeFile
                this.storePassword = storePassword ?: "storePassword"
                this.keyAlias = keyAlias ?: "keyAlias"
                this.keyPassword = keyPassword ?: "keyPassword"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
            if (storeFile != null) {
                signingConfig = this@android.signingConfigs.getByName(signingConfigName)
            }
        }
    }

    val versionFlavorDimension = "version"
    flavorDimensions.add(versionFlavorDimension)
    productFlavors {
        create("qualif") {
            dimension = versionFlavorDimension
            applicationId = "com.orange.ods.test.app"
        }

        create("prod") {
            dimension = versionFlavorDimension
            applicationId = "com.orange.ods.app"
        }
    }

    firebaseAppDistribution {
        appId = "1:212698857200:android:67d1403d02a72f4d5ecc35"
        releaseNotesFile = Firebase_gradle.AppDistribution.releaseNotesFilePath
        groups = project.findTypedProperty("appDistributionGroup")
    }

    kotlin {
        jvmToolchain(17)
        compilerOptions {
            allWarningsAsErrors = true
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            // Suppresses an expected warning that triggers a build failure because allWarningsAsErrors is true
            // See https://youtrack.jetbrains.com/issue/KT-68400/K2-w-Kapt-currently-doesnt-support-language-version-2.0.-Falling-back-to-1.9.
            freeCompilerArgs.add("-Xsuppress-version-warnings")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
        dataBinding = true
    }

    packaging {
        with(resources.excludes) {
            add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":lib"))
    implementation(project(":lib-xml"))
    implementation(project(":module-about"))
    implementation(project(":module-more-apps"))
    implementation(project(":theme-innovation-cup"))

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.browser)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.webkit)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlin.reflect)
    implementation(libs.material)
    implementation(libs.timber)
}

tasks.register<Copy>("copyChangelog") {
    from("../changelog.md").into("src/main/res/raw")
}

tasks.register<Copy>("copyThirdParty") {
    from("../THIRD_PARTY.md").into("src/main/res/raw").rename { it.lowercase() }
}

gradle.projectsEvaluated {
    tasks.named("preBuild").dependsOn(tasks.named("copyChangelog"), tasks.named("copyThirdParty"))
}
