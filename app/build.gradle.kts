plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

android {
    namespace = "com.example.testing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testing"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    //implementation(libs.mediation.test.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation (libs.ui) // or latest version
    implementation (libs.androidx.material) // or latest version
    implementation (libs.ui.tooling.preview) // optional for preview

    // Navigation Component for Compose
    implementation (libs.androidx.navigation.compose) // or latest version

    // Optional - If you are using Hilt for dependency injection
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.gson)

    implementation (libs.retrofit) // Retrofit core library

    // Gson Converter
    implementation (libs.converter.gson)
    implementation (libs.hilt.android)
    implementation (libs.coil.compose)
    //noinspection GradleDependency
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.retrofit)// Retrofit with Scalar
    implementation(libs.converter.scalars)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.converter.scalars)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)

    implementation (libs.kotlinx.coroutines.android)

    // Coil for image loading

    implementation (libs.androidx.lifecycle.viewmodel.compose)
    //noinspection GradleDependency
    implementation (libs.androidx.lifecycle.runtime.compose)

    implementation (libs.logging.interceptor)

    //serialization
    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json.v163)
    // Retrofit with scalar Converter
}