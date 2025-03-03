package com.example.vangtichai

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyApp() }
    }
}

@Composable
fun MyApp() {
    ScreenLayout()
}

@Composable
fun ScreenLayout() {
    var amount by rememberSaveable { mutableStateOf("") }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeLayout(amount) { amount = it }
    } else {
        PortraitLayout(amount) { amount = it }
    }
}

@Composable
fun LandscapeLayout(amount: String, onAmountChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Header()
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                AmountDisplay(amount)
                Spacer(modifier = Modifier.height(16.dp))
                ChangeBreakdown(amount)
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                NumberPad(onDigitClick = { digit -> onAmountChange(amount + digit) }, onClear = { onAmountChange("") })
            }
        }
    }
}

@Composable
fun PortraitLayout(amount: String, onAmountChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        AmountDisplay(amount)
        Spacer(modifier = Modifier.height(16.dp))
        NumberPad(onDigitClick = { digit -> onAmountChange(amount + digit) }, onClear = { onAmountChange("") })
        Spacer(modifier = Modifier.height(16.dp))
        ChangeBreakdown(amount)
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFF4CAF93)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = "Vangti Chai", modifier = Modifier.padding(start = 32.dp, top = 16.dp), color = Color.White, fontSize = 30.sp)
    }
}

@Composable
fun AmountDisplay(amount: String) {
    Text(
        text = if (amount.isEmpty()) "Enter Amount" else "Taka: $amount",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NumberPad(onDigitClick: (String) -> Unit, onClear: () -> Unit) {
    val buttons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("Clear", "0")
    )

    Column {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { digit ->
                    if (digit == "Clear") {
                        ClearButton(onClear)
                    } else {
                        NumberButton(digit, onDigitClick)
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(digit: String, onDigitClick: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp) // Added margin around the button
            .size(80.dp, 60.dp)
            .background(Color.Gray)
            .clickable { onDigitClick(digit) }
    ) {
        Text(text = digit, fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun ClearButton(onClear: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp) // Added margin around the clear button
            .size(160.dp, 60.dp)
            .background(Color.Gray)
            .clickable { onClear() }
    ) {
        Text(text = "Clear", fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun ChangeBreakdown(amount: String) {
    val denominations = listOf(500, 100, 50, 20, 10, 5, 1)
    val amountInt = amount.toIntOrNull() ?: 0
    var remainingAmount = amountInt

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp)

    ) {
        Spacer(modifier = Modifier.height(8.dp))
        denominations.forEach { denomination ->
            val count = remainingAmount / denomination
            remainingAmount %= denomination
            Text(
                text = "$denomination: $count",
                fontSize = 34.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Portrait Mode")
@Composable
fun PreviewPortrait() {
    MyApp()
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280, name = "Tablet Mode")
@Composable
fun PreviewTablet() {
    MyApp()
}

@Preview(showBackground = true, widthDp = 731, heightDp = 411, name = "Landscape Mode")
@Composable
fun PreviewLandscape() {
    MyApp()
}
