package com.example.demoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.demoapp.theme.DemoAppTheme
import com.paygen.sdk.models.PayGateColors
import com.paygen.sdk.models.PayGateConfig
import com.paygen.sdk.models.PayGateEnv
import com.paygen.sdk.models.PaymentResult
import com.paygen.sdk.ui.CheckoutButton
import com.paygen.sdk.ui.PayGateProvider
import com.paygen.sdk.ui.PaymentSheet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val config = PayGateConfig(
                        merchantId = "merch_123",
                        merchantName = "Demo Store",
                        env = PayGateEnv.sandbox, // Use sandbox for testing
                        currency = "USD",
                        colors = PayGateColors(
                            primary = "#1E3A8A", // Blue brand
                            background = "#FFFFFF",
                            surface = "#F3F4F6",
                            text = "#1F2937"
                        )
                    )

                    PayGateProvider(config = config) {
                        MyCheckoutScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MyCheckoutScreen() {
    val context = LocalContext.current
    var showPaymentSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your Cart Total: $99.99", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 32.dp))

        CheckoutButton(
            amount = 99.99,
            onClick = { showPaymentSheet = true }
        )

        PaymentSheet(
            visible = showPaymentSheet,
            amount = 99.99,
            onComplete = { result ->
                when (result) {
                    is PaymentResult.Success -> {
                        Toast.makeText(context, "Payment Succeeded: ${result.transactionId}", Toast.LENGTH_LONG).show()
                    }
                    is PaymentResult.Failure -> {
                        Toast.makeText(context, "Payment Failed: ${result.message}", Toast.LENGTH_LONG).show()
                    }
                }
            },
            onDismiss = {
                showPaymentSheet = false
            }
        )
    }
}
