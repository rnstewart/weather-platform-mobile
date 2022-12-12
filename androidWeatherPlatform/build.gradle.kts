plugins {
    id("com.android.application")
    kotlin("android")
    id("com.squareup.sqldelight")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    kotlin("plugin.serialization")
}

val composeVersion = findProperty("composeVersion") as String
val navigationVersion = findProperty("navigationVersion")
val ktorVersion = findProperty("ktorVersion")
val okHttpVersion = findProperty("okHttpVersion")
val napierVersion = findProperty("napierVersion")
val sqlDelightVersion = findProperty("sqlDelightVersion")
val coroutinesVersion = findProperty("coroutinesVersion")
val klockVersion = findProperty("klockVersion")
val serializationVersion = findProperty("serializationVersion")
val firebaseCrashlyticsVersion = findProperty("firebaseCrashlyticsVersion")
val androidMaterialVersion = findProperty("androidMaterialVersion")
val appCompatVersion = findProperty("appCompatVersion")
val kodeinVersion = findProperty("kodeinVersion")

val lifecycleVersion = "1.1.1"

android {
    val compileSdkVersion = (findProperty("compileSdk") as String).toInt()
    val minSdkVersion = (findProperty("minSdk") as String).toInt()
    val targetSdkVersion = (findProperty("targetSdk") as String).toInt()

    val majorVersion = (findProperty("majorVersion") as String).toInt()
    val minorVersion = (findProperty("minorVersion") as String).toInt()
    val patchVersion = (findProperty("patchVersion") as String).toInt()
    val testVersion = (findProperty("testVersion") as String).toInt()

    compileSdk = compileSdkVersion
    defaultConfig {
        applicationId = "com.zmosoft.weatherplatform.android"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = (majorVersion * 100000) + (minorVersion * 1000) + (patchVersion * 100) + testVersion
        versionName = if (testVersion > 0) {
            "${majorVersion}.${minorVersion}.${patchVersion}.${testVersion}"
        } else {
            "${majorVersion}.${minorVersion}.${patchVersion}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), File("proguard-rules.pro"))
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    namespace = "com.zmosoft.weatherplatform.android"
}

dependencies {
    implementation(project(":sharedWeatherPlatform"))
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("android.arch.lifecycle:extensions:$lifecycleVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
    implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("io.github.aakira:napier:$napierVersion")
    implementation("org.kodein.di:kodein-di-framework-android-core:$kodeinVersion")
    implementation("org.kodein.di:kodein-di-framework-android-support:$kodeinVersion")
    implementation("org.kodein.di:kodein-di-framework-android-x:$kodeinVersion")
    implementation("io.coil-kt:coil-compose:1.4.0")
}