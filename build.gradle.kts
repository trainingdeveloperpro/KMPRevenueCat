import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Properties

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.reader())
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinNativeCocoaPods) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlinx.binary.validator)
}




allprojects {
    group = "io.github.mirzemehdi"
    version = project.properties["kmpRevenueCatVersion"] as String
    val sonatypeUsername = gradleLocalProperties(rootDir).getProperty("sonatypeUsername")
    val sonatypePassword = gradleLocalProperties(rootDir).getProperty("sonatypePassword")
    val gpgKeySecret = gradleLocalProperties(rootDir).getProperty("gpgKeySecret")
    val gpgKeyPassword = gradleLocalProperties(rootDir).getProperty("gpgKeyPassword")

    val excludedModules = listOf(":sampleApp:composeApp",":sampleApp")
    if (project.path in excludedModules) return@allprojects

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")


    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/trainingdeveloperpro/KMPRevenueCat")
                credentials {
                    username = localProperties.getProperty("GITHUB_USERNAME")
                    password = localProperties.getProperty("GITHUB_TOKEN")
                }
            }
        }

        val javadocJar = tasks.register<Jar>("javadocJar") {
            dependsOn(tasks.getByName<DokkaTask>("dokkaHtml"))
            archiveClassifier.set("javadoc")
            from("${layout.buildDirectory}/dokka")
        }

        publications {
            withType<MavenPublication> {
                artifact(javadocJar)
                pom {
                    groupId = "io.github.trainingdeveloperpro"
                    name.set("KMPRevenueCat")
                    description.set("KMPRevenueCat is an unofficial Kotlin Multiplatform library, wrapper library around RevenueCat integration with a Kotlin-first approach, offering a unified API for subscription/in-app purchases across iOS, and Android.")
                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://opensource.org/licenses/Apache-2.0")
                        }
                    }
                    url.set("mirzemehdi.github.io/KMPRevenueCat/")
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/mirzemehdi/KMPRevenueCat/issues")
                    }
                    scm {
                        connection.set("https://github.com/trainingdeveloperpro/KMPRevenueCat.git")
                        url.set("https://github.com/trainingdeveloperpro/KMPRevenueCat")
                    }
                    developers {
                        developer {
                            name.set("Mirzamehdi Karimov")
                            email.set("mirzemehdi@gmail.com")
                        }
                    }
                }
            }
        }
    }

    val publishing = extensions.getByType<PublishingExtension>()
    extensions.configure<SigningExtension> {
        useInMemoryPgpKeys(gpgKeySecret, gpgKeyPassword)
        sign(publishing.publications)
    }

    // TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
    project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        dependsOn(project.tasks.withType(Sign::class.java))
    }
}


