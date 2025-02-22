package com.example.vangtichai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VangtiChaiApp()
        }
    }
}

@Composable
fun VangtiChaiApp() {
    var amount by remember { mutableStateOf("") }
    val notes = listOf(500, 100, 50, 20, 10, 5, 2, 1)
    val changeMap = remember(amount) { calculateChange(amount, notes) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ChangeDisplay(changeMap, Modifier.weight(1f))
        NumericKeypad(
            onDigitPress = { digit -> amount += digit },
            onClearPress = { amount = "" },
            Modifier.weight(1f)
        )
    }
}

@Composable
fun NumericKeypad(onDigitPress: (String) -> Unit, onClearPress: () -> Unit, modifier: Modifier) {
    val digits = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("0")
    )

    Column(modifier = modifier.padding(16.dp)) {
        digits.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { digit ->
                    Button(
                        onClick = { onDigitPress(digit) },
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0C0C0))
                    ) {
                        Text(text = digit, fontSize = 20.sp, color = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = onClearPress,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "CLEAR", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun ChangeDisplay(changeMap: Map<Int, Int>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Text("Change Breakdown", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        changeMap.forEach { (note, count) ->
            Text(text = "$note: $count", fontSize = 18.sp)
        }
    }
}

fun calculateChange(amount: String, notes: List<Int>): Map<Int, Int> {
    val result = mutableMapOf<Int, Int>()
    var remaining = amount.toIntOrNull() ?: 0
    for (note in notes) {
        result[note] = remaining / note
        remaining %= note
    }
    notes.forEach { result.putIfAbsent(it, 0) }
    return result
}
