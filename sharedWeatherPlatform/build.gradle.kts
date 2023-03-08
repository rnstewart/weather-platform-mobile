plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization")
}

val majorVersion = (findProperty("majorVersion") as String).toInt()
val minorVersion = (findProperty("minorVersion") as String).toInt()
val patchVersion = (findProperty("patchVersion") as String).toInt()
val testVersion = (findProperty("testVersion") as String).toInt()

version = if (testVersion > 0) {
    "${majorVersion}.${minorVersion}.${patchVersion}.${testVersion}"
} else {
    "${majorVersion}.${minorVersion}.${patchVersion}"
}

kotlin {
    val ktorVersion = findProperty("ktorVersion")
    val okHttpVersion = findProperty("okHttpVersion")
    val sqlDelightVersion = findProperty("sqlDelightVersion")
    val napierVersion = findProperty("napierVersion")
    val coroutinesVersion = findProperty("coroutinesVersion")
    val klockVersion = findProperty("klockVersion")
    val serializationVersion = findProperty("serializationVersion")
    val firebaseCrashlyticsVersion = findProperty("firebaseCrashlyticsVersion")
    val androidMaterialVersion = findProperty("androidMaterialVersion")
    val kodeinVersion = findProperty("kodeinVersion")

    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosWeatherPlatform/Podfile")
        framework {
            baseName = "sharedWeatherPlatform"
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("io.ktor.util.InternalAPI")
        }
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
                implementation("io.github.aakira:napier:$napierVersion")
                api("org.kodein.di:kodein-di:$kodeinVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:$androidMaterialVersion")
                implementation(kotlin("stdlib"))
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-auth-jvm:$ktorVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
                implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
                implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
                implementation("org.slf4j:slf4j-api:1.7.32")
                implementation("uk.uuid.slf4j:slf4j-android:1.7.30-0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
                implementation("com.google.firebase:firebase-crashlytics:$firebaseCrashlyticsVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    val compileSdkVersion = (findProperty("compileSdk") as String).toInt()
    val minSdkVersion = (findProperty("minSdk") as String).toInt()
    val targetSdkVersion = (findProperty("targetSdk") as String).toInt()

    compileSdk = compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    namespace = "com.zmosoft.weatherplatform"

    sqldelight {
        database("WeatherPlatformDB") {
            packageName = "com.zmosoft.weatherplatform"
            sourceFolders = listOf("sqldelight")
        }
    }
}