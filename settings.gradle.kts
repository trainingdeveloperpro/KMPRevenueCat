rootProject.name = "KMPRevenueCatLib"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":kmprevenuecat-purchases")
include(":kmprevenuecat-purchases-ui")
include(":sampleApp:composeApp")
include(":sampleApp:desktopApp")
