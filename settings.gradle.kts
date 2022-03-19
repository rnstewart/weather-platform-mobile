pluginManagement {
    val kotlinVersion: String by settings

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}

rootProject.name = "Weather_Platform"
include(":androidWeatherPlatform")
include(":sharedWeatherPlatform")