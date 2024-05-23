import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

kotlin {

    jvm {}
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.koin.core)
                implementation(project(":sampleApp:composeApp"))
//                api(libs.imageLoader.extension.imageio)
            }
        }
    }
}

configurations {
    getByName("jvmMainImplementation") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android") // #57 Fix some library loading coroutines for Android -> JVM getting Main dispatcher for Android -> exception
    }
    getByName("jvmTestImplementation") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
    }
}

compose.desktop {

    application {
        mainClass = "com.mmk.kmprevenuecat.sample.MainKt"

        nativeDistributions {

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MangalaWallet"
            packageVersion = "1.0.0"

            windows {
                menu = true
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "AF792DA6-2EA3-495A-95E5-C3C6CBCB9948"
            }

            macOS {
                // Use -Pcompose.desktop.mac.sign=true to sign and notarize.
                bundleID ="com.mmk.kmprevenuecat"
            }
        }
    }
}
