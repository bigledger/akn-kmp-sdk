import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.akaun.kmp.sdk"
version = property("aknVersion") as String

kotlin {
    jvm()
    androidLibrary {
        namespace = "com.akaun.kmp.sdk"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(
                    JvmTarget.JVM_11
                )
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // api: HttpClient, JsonElement/JsonObject and kotlin.time.Instant (with kotlinx-datetime for time zones) are part of the SDK's public API
            api(libs.ktor.client.core)
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.datetime)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
        }
    }
}

mavenPublishing {
    coordinates(group.toString(), "akn-kmp-sdk", version.toString())

    pom {
        name = "akn-kmp-sdk"
        description = "Kotlin Multiplatform Ktor Client SDK for the AKAUN platform"
        inceptionYear = "2025"
        url = "https://github.com/bigledger/akn-kmp-sdk"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "bigledger"
                name = "Bigledger"
                url = "https://github.com/bigledger"
            }
        }
        scm {
            url = "https://github.com/bigledger/akn-kmp-sdk"
            connection = "scm:git:git://github.com/bigledger/akn-kmp-sdk.git"
            developerConnection = "scm:git:ssh://git@github.com/bigledger/akn-kmp-sdk.git"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bigledger/akn-kmp-sdk")
            credentials {
                // CI supplies these. Locally, put gpr.user / gpr.key in ~/.gradle/gradle.properties, never in this repo.
                username = System.getenv("GITHUB_ACTOR") ?: findProperty("gpr.user") as String?
                password = System.getenv("GITHUB_TOKEN") ?: findProperty("gpr.key") as String?
            }
        }
    }
}
