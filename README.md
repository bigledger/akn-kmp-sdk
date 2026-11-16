# akn-kmp-sdk

Kotlin Multiplatform SDK for the akaun API (Android, JVM, iOS), built on Ktor client + kotlinx.serialization.
It replaces the JVM-only Retrofit SDK [`akn-kotlin-sdk`](https://github.com/bigledger/akn-kotlin-sdk).

**Status:** models and services are migrated. See epic #3.

## Install

The SDK is on GitHub Packages. GitHub asks for a login even for public packages, so create a personal access token (classic) with the `read:packages` scope and add it to your user-level `~/.gradle/gradle.properties`, never to the app's repo:

```properties
gpr.user=<your GitHub username>
gpr.key=<the token>
```

Then in the app's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/bigledger/akn-kmp-sdk")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

and add the dependency (to `commonMain` in a KMP app, or to an Android module's `dependencies`):

```kotlin
implementation("com.akaun.kmp.sdk:akn-kmp-sdk:0.1.0")
```

In an app's CI, store a `read:packages` token as a secret and pass it in as the `GITHUB_TOKEN` environment variable (with `GITHUB_ACTOR` set to its owner).

## Releasing

1. Bump `aknVersion` in `gradle.properties` and merge it.
2. Push the tag `v<aknVersion>`, e.g. `git tag v0.1.0 && git push origin v0.1.0`.

The Publish workflow checks that the tag matches `aknVersion`, then publishes. A published version can never be replaced, so a bad release needs a new version.

## Usage

```kotlin
val client = createAknHttpClient(
    baseUrl = Core2Config.CLOUD_URL,
    authorization = token,          // sent as the Authorization header
    tenantCode = "my_tenant",       // sent as the tenantCode header
)
// Services (from #6) take this client: LoginService(client).login(...)
```

- Non-2xx responses throw `io.ktor.client.plugins.ResponseException`.
- Logging is off by default (`logLevel = LogLevel.NONE`). `Authorization` is always masked, but `LogLevel.BODY` logs request bodies, including passwords.
- JSON settings (`AknJson`) mimic the old GSON behaviour: unknown keys ignored, nulls fall back to defaults, numbers accepted in `String` fields.
- Apps need **Kotlin 2.3 or newer**: table-row date fields are `kotlin.time.Instant` (stable since 2.3). `kotlinx-datetime` comes with the SDK for time zones, e.g. `row.date_created?.toLocalDateTime(TimeZone.currentSystemDefault())`.
- Table-row fields are typed from the backend columns: GUIDs, codes and enums are `String?`, amounts `Double?`, JSON columns `JsonObject?`.

## Tests

```
./gradlew jvmTest testAndroidHostTest     # any OS (needs JDK 17–21)
./gradlew iosSimulatorArm64Test           # macOS only
```

To test a model or service, use `MockAknClient` in `commonTest` (`support/MockAknClient.kt`). It answers every request with a canned JSON response and records the requests for assertions.
