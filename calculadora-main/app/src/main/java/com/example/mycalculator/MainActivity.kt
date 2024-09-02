package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalculator.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var displayText by remember { mutableStateOf("0") }
    var lastNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(fontSize = 48.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
                .align(Alignment.End)
        )

        Column {
            Row {
                CalculatorButton("7") { numberClicked("7", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("8") { numberClicked("8", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("9") { numberClicked("9", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("/") { operatorClicked("/", onOperatorChange = { operator = it }) }
            }
            Row {
                CalculatorButton("4") { numberClicked("4", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("5") { numberClicked("5", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("6") { numberClicked("6", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("*") { operatorClicked("*", onOperatorChange = { operator = it }) }
            }
            Row {
                CalculatorButton("1") { numberClicked("1", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("2") { numberClicked("2", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("3") { numberClicked("3", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("-") { operatorClicked("-", onOperatorChange = { operator = it }) }
            }
            Row {
                CalculatorButton("C") { clearClicked(onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }, onOperatorChange = { operator = it }) }
                CalculatorButton("0") { numberClicked("0", displayText, lastNumber, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }) }
                CalculatorButton("=") { equalsClicked(lastNumber, displayText, operator, onDisplayChange = { displayText = it }, onLastNumberChange = { lastNumber = it }, onOperatorChange = { operator = it }) }
                CalculatorButton("+") { operatorClicked("+", onOperatorChange = { operator = it }) }
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp)
    ) {
        Text(text = text, fontSize = 24.sp)
    }
}

fun numberClicked(number: String, displayText: String, lastNumber: String, operator: String, onDisplayChange: (String) -> Unit, onLastNumberChange: (String) -> Unit) {
    if (operator.isEmpty()) {
        onDisplayChange(if (displayText == "0") number else displayText + number)
    } else {
        onLastNumberChange(if (lastNumber.isEmpty()) number else lastNumber + number)
        onDisplayChange(lastNumber + number)
    }
}

fun operatorClicked(newOperator: String, onOperatorChange: (String) -> Unit) {
    onOperatorChange(newOperator)
}

fun clearClicked(onDisplayChange: (String) -> Unit, onLastNumberChange: (String) -> Unit, onOperatorChange: (String) -> Unit) {
    onDisplayChange("0")
    onLastNumberChange("")
    onOperatorChange("")
}

fun equalsClicked(lastNumber: String, displayText: String, operator: String, onDisplayChange: (String) -> Unit, onLastNumberChange: (String) -> Unit, onOperatorChange: (String) -> Unit) {
    val result = when (operator) {
        "+" -> (lastNumber.toDoubleOrNull() ?: 0.0) + (displayText.toDoubleOrNull() ?: 0.0)
        "-" -> (lastNumber.toDoubleOrNull() ?: 0.0) - (displayText.toDoubleOrNull() ?: 0.0)
        "*" -> (lastNumber.toDoubleOrNull() ?: 0.0) * (displayText.toDoubleOrNull() ?: 0.0)
        "/" -> {
            val divisor = displayText.toDoubleOrNull() ?: 0.0
            if (divisor == 0.0) "Err" else (lastNumber.toDoubleOrNull() ?: 0.0) / divisor
        }
        else -> displayText
    }

    onDisplayChange(result.toString())
    onLastNumberChange("")
    onOperatorChange("")
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MyApplicationTheme {
        CalculatorScreen()
    }
}
