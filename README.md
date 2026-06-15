# PayGen Android SDK

A fully-featured, secure, and PCI DSS compliant Payment Gateway UI library for Android built with Jetpack Compose.

## Features
- **Modern Jetpack Compose UI**: Beautiful, interactive cards, smooth animations, and theming.
- **PCI DSS Compliant**: Leverages `FLAG_SECURE` to prevent screen recording and screenshots while payment information is entered, and disables OS-level keyboard caching.
- **Multiple Payment Methods**: Supports Credit/Debit Cards, Google Pay, and UPI.
- **Live Validations**: Real-time validation and visual transformations for Card Numbers, Expiry Dates, and CVV.
- **Dynamic Theming**: Completely customize the colors to match your app's brand.

---

1. Add the raw GitHub maven repository in your `settings.gradle.kts` or project `build.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://raw.githubusercontent.com/Akash-80442/paygen-sdk-releases/maven-repo")
        }
    }
}
```

2. Add the dependency to your app-level `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.paygen.sdk:paygen-sdk:1.1.2")
}
```

---

## Usage

### 1. Wrap your app or screen with `PayGateProvider`
The SDK uses `PayGateProvider` to supply configuration and state down to the components.

```kotlin
import com.paygen.sdk.ui.PayGateProvider
import com.paygen.sdk.models.PayGateConfig
import com.paygen.sdk.models.PayGateEnv
import com.paygen.sdk.models.PayGateColors

val config = PayGateConfig(
    merchantId = "merch_123",
    merchantName = "My Awesome Store",
    env = PayGateEnv.sandbox, // Use sandbox for testing
    currency = "USD",
    colors = PayGateColors(
        primary = "#C77C40", // Brand Color
        background = "#FAF9F6",
        surface = "#E5E7EB",
        text = "#111827"
    )
)

PayGateProvider(config = config) {
    // Your app content here
    MyCheckoutScreen()
}
```

### 2. Trigger the Payment Sheet
Inside your screen, simply trigger the `PaymentSheet` when a user wants to pay.

```kotlin
import androidx.compose.runtime.*
import com.paygen.sdk.ui.PaymentSheet
import com.paygen.sdk.ui.CheckoutButton

@Composable
fun MyCheckoutScreen() {
    var showPaymentSheet by remember { mutableStateOf(false) }

    // Optional: Use the built-in CheckoutButton
    CheckoutButton(
        amount = 99.99,
        onClick = { showPaymentSheet = true }
    )

    // The Bottom Sheet UI
    PaymentSheet(
        visible = showPaymentSheet,
        amount = 99.99,
        onComplete = { result ->
            when (result) {
                is PaymentResult.Success -> {
                    println("Payment Succeeded! TXN ID: ${result.transactionId}")
                }
                is PaymentResult.Failure -> {
                    println("Payment Failed: ${result.message}")
                }
            }
        },
        onDismiss = {
            showPaymentSheet = false
        }
    )
}
```

## Security & Obfuscation
The SDK comes pre-configured with ProGuard/R8 rules. When you build your application for release, all of the internal logic, API handling, and UI formatting of PayGen is automatically obfuscated and shrunk to protect against reverse engineering. You don't need to add any custom rules to your app's `proguard-rules.pro`.

## Running the Example App
This repository contains a full  demonstrating how to integrate the PayGen SDK.
To run it:
1. Clone this repository to your machine.
2. Open the  folder in Android Studio.
3. Run it on an emulator or physical device!
