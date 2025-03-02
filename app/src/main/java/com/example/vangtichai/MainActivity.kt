package com.example.vangtichai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var amount by remember { mutableStateOf("") }


    Column( modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF4CAF50)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Vangti Chai",
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AmountDisplay(amount)
            Spacer(modifier = Modifier.height(16.dp))
            NumberPad(onDigitClick = { digit ->
                amount += digit
            }, onClear = { amount = "" })
            Spacer(modifier = Modifier.height(16.dp))
            ChangeBreakdown(amount)
        }
    }
}

@Composable
fun AmountDisplay(amount: String) {
    Text(
        text = if (amount.isEmpty()) "Enter Amount" else "Taka: $amount",
        fontSize = 32.sp,
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        denominations.forEach { denomination ->
            val count = remainingAmount / denomination
            remainingAmount %= denomination
            Text(
                text = "$denomination: $count",
                fontSize = 28.sp
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
