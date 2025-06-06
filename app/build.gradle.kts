plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin") // Ensure this is applied
    id("kotlinx-serialization")
}

android.buildFeatures.buildConfig = true

android {
    namespace = "com.example.maverickshows"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.maverickshows"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "TMDB_API", "\"${project.findProperty("TMDB_API")}\"")

        // For manifest placeholders
        manifestPlaceholders["TMDB_API"] = project.findProperty("TMDB_API") ?: ""

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)

    //navigation
    val navVersion = "2.9.0"
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // JSON serialization library, works with the Kotlin serialization plugin
    implementation(libs.kotlinx.serialization.json)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Compose BOM - manages all Compose library versions
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)

    // Kotlin serialization with retrofit
    implementation(libs.jakewharton.retrofit2.kotlinx.serialization.converter)

    // Kotlinx Serialization JSON
    implementation(libs.kotlinx.serialization.json.v162)

    // Coil for image loading in Compose
    implementation(libs.coil.compose)

    // Dagger Hilt
    // Ensure libs.hilt.android and libs.hilt.compiler use the exact same version
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt Navigation Compose - This is usually sufficient for ViewModel injection in Compose
    implementation(libs.androidx.hilt.navigation.compose)

    // ViewModel Compose - Good to keep for general ViewModel usage
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.core)
    implementation( libs.chromecast.sender)
}
