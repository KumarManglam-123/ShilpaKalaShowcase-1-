plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.shilpakala.showcase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shilpakala.showcase"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "com.shilpakala.showcase.HiltTestRunner"

        // Build config for API keys
        buildConfigField("String", "GEMINI_API_KEY", "\"${project.findProperty("GEMINI_API_KEY") ?: ""}\"")
        buildConfigField("String", "DB_PASSPHRASE", "\"${project.findProperty("DB_PASSPHRASE") ?: "shilpakala_secure_2024"}\"")
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.generateKotlin", "true")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"
        }
    }
}

dependencies {
    // Compose BOM
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.animation)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Activity
    implementation(libs.activity.compose)

    // Core
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Navigation
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    // DataStore
    implementation(libs.datastore.preferences)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Image Loading
    implementation(libs.coil.compose)

    // WorkManager
    implementation(libs.work.runtime)
    implementation(libs.hilt.work)
    ksp(libs.hilt.work.compiler)

    // Security
    implementation(libs.sqlcipher)
    implementation(libs.sqlite)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Accompanist
    implementation(libs.accompanist.permissions)

    // ExifInterface
    implementation(libs.exifinterface)

    // Gson
    implementation(libs.gson)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.core.testing)

    // Android Testing
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.hilt.android)
    kspAndroidTest(libs.hilt.compiler)
}
