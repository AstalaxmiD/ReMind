

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // Or "com.android.application"
    // Or "org.jetbrains.kotlin.android"
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.remind"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.remind"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true // <<< ESSENTIAL
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13" // <<< VERSION MUST BE COMPATIBLE
    }

    buildFeatures {
        compose = true
    }
    //composeOptions {
      //  kotlinCompilerExtensionVersion = "1.5.3" // Or your compatible version
    //}

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
        viewBinding = true
    }
}

dependencies {


        val composeBom = platform("androidx.compose:compose-bom:2023.10.01") // Or latest BOM
        implementation(composeBom)
        androidTestImplementation(composeBom)

    // Or for Material 2 components (older)
    // For Glance AppWidgets
    implementation("androidx.glance:glance-appwidget:1.1.0-beta01") // Use the latest stable or beta version

    // Optional: If you want to use Material 3 components in your Glance widget
    implementation("androidx.glance:glance-material3:1.1.0-beta01")
    // Or for Material 2 components (older)

        implementation("com.google.code.gson:gson:2.10.1")

        // Choose one of the following Material Design implementations
        implementation("androidx.compose.material3:material3")
        // or Material Design 2
        // implementation("androidx.compose.material:material")
        // or skip Material Design and build directly on top of foundational components
        // implementation("androidx.compose.foundation:foundation")
        // or build components usable across Material Design versions
        // implementation("androidx.compose.material:material-core")

        // Android Studio Preview support
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")

        // UI Tests
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-test-manifest")

        // Integration with Activities
        implementation("androidx.activity:activity-compose:1.8.0") // Or latest

        // Add other Compose libraries as needed (ViewModel, LiveData, Navigation etc.)
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}