plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    kotlin("jvm") apply false
    id("com.google.dagger.hilt.android") version "2.50" apply true
}

android {
    namespace = "com.example.appcsn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appcsn"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.javapoet)

    // retrofit
    implementation(libs.retrofit)

    // GSON
    implementation(libs.converter.gson)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.coil.compose)

    implementation(libs.core)
    implementation(("androidx.compose.material3:material3:1.2.0-beta02"))
    implementation(libs.androidx.material)
    ksp(libs.ksp)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.font.awesome)
    implementation(libs.simple.icons)
    implementation(libs.feather)
    implementation(libs.css.gg)
    implementation(libs.tabler.icons)
    implementation(libs.eva.icons)
    implementation(libs.octicons)
    implementation(libs.linea)
    implementation(libs.line.awesome)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
