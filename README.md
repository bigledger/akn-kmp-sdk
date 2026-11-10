# akn-kmp-sdk

Kotlin Multiplatform SDK for the akaun API (Android, JVM, iOS), built on Ktor client + kotlinx.serialization.
It replaces the JVM-only Retrofit SDK [`akn-kotlin-sdk`](https://github.com/bigledger/akn-kotlin-sdk).

**Status:** foundation only (#4). Models (#5) and services (#6) are being migrated. See epic #3.

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

## Tests

```
./gradlew jvmTest testAndroidHostTest     # any OS (needs JDK 17–21)
./gradlew iosSimulatorArm64Test           # macOS only
```

To test a model or service, use `MockAknClient` in `commonTest` (`support/MockAknClient.kt`). It answers every request with a canned JSON response and records the requests for assertions.
