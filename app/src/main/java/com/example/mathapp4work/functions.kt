package com.example.mathapp4work

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlin.math.abs

@SuppressLint("DefaultLocale")
@Composable
fun AppContent() {
    var values by remember {
        mutableStateOf(
            mapOf(
                "xFirst" to "",
                "zFirst" to "",
                "xSecond" to "",
                "zSecond" to "",
                "xThird" to "",
                "zThird" to "",
                "diametr" to "",
                "segment" to "",
                "canvas" to "",
                "acValue" to "",
                "hValue" to "",
                "zValue" to ""
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item { InputFieldsSection(values) { key, value -> values = values + (key to value) } }

        item { ImageBoxesSection() }

        item { InputXZSection(values) { key, value -> values = values + (key to value) } }

        item { ThirdImageAndInputSection(values) { key, value -> values = values + (key to value) } }

        item { ResultImageSection() }

        item { ResultInputSection(values) }

        item { SubmitButton(values) { updatedValues -> values = updatedValues } }
    }
}

@Composable
fun InputFieldsSection(values: Map<String, String>, onValueChange: (String, String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listOf("diametr" to "diametr =", "segment" to "segment =", "canvas" to "canvas(полотно) = ").forEach { (key, label) ->
            VariableTextField(label, values[key] ?: "") { onValueChange(key, it) }
        }
    }
}

@Composable
fun ImageBoxesSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf(R.drawable.testone to Color.Green, R.drawable.testtwo to Color.Red).forEach { (image, color) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Black.copy(alpha = 0.3f),
                            size = size.copy(width = size.width + 8.dp.toPx(), height = size.height + 8.dp.toPx()),
                            cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx()),
                            topLeft = Offset(-4.dp.toPx(), -4.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                ) {
                    AsyncImage(model = image, contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun InputXZSection(values: Map<String, String>, onValueChange: (String, String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = Modifier.weight(0.5f)
            .background(Color.Green)
            .clip(RoundedCornerShape(16.dp))) {
            listOf("xFirst" to "X =", "zFirst" to "Z =").forEach { (key, label) ->
                VariableTextField(label, values[key] ?: "") { onValueChange(key, it) }
            }
        }
        Column(modifier = Modifier.weight(0.5f)
            .background(Color.Red)
            .clip(RoundedCornerShape(16.dp))){
            listOf("xSecond" to "X =", "zSecond" to "Z =").forEach { (key, label) ->
                VariableTextField(label, values[key] ?: "") { onValueChange(key, it) }
            }
        }
    }
}

@Composable
fun ThirdImageAndInputSection(values: Map<String, String>, onValueChange: (String, String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(modifier = Modifier.weight(0.6f).height(200.dp), contentAlignment = Alignment.Center) {
            AsyncImage(model = R.drawable.testthree, contentDescription = null)
        }
        Box(modifier = Modifier.weight(0.4f).height(150.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                listOf("xThird" to "X =", "zThird" to "Z =").forEach { (key, label) ->
                    VariableTextField(label, values[key] ?: "") { onValueChange(key, it) }
                }
            }
        }
    }
}

@Composable
fun ResultImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(model = R.drawable.result, contentDescription = null)
    }
}

@Composable
fun ResultInputSection(values: Map<String, String>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("----Result-------------------------------------------------------")
        listOf("acValue" to "AC =", "hValue" to "H =", "zValue" to "Z =").forEach { (key, label) ->
            // Отображаем результат как текст, а не как поле ввода
            Text(text = "$label ${values[key] ?: ""}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun SubmitButton(values: Map<String, String>, onValuesUpdated: (Map<String, String>) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(25  .dp),
        contentAlignment = Alignment.Center) {
        Button(onClick = {
            val updatedValues = values.toMutableMap()
            updatedValues["acValue"] = String.format("%.4f", calculateAC(
                updatedValues["xThird"] ?: "", updatedValues["xSecond"] ?: "",
                updatedValues["zThird"] ?: "", updatedValues["zSecond"] ?: "",
                updatedValues["xFirst"] ?: "", updatedValues["canvas"] ?: "", updatedValues["segment"] ?: ""
            ))

            updatedValues["hValue"] = String.format("%.4f", calculateH(
                updatedValues["xSecond"] ?: "", updatedValues["xThird"] ?: "",
                updatedValues["zSecond"] ?: "", updatedValues["zThird"] ?: ""
            ))

            updatedValues["zValue"] = String.format("%.4f", calculateZ(
                updatedValues["xThird"] ?: "", updatedValues["xSecond"] ?: "",
                updatedValues["zSecond"] ?: "", updatedValues["zThird"] ?: "",
                updatedValues["diametr"] ?: ""
            ))

            onValuesUpdated(updatedValues)
        }) {
            Text("Submit")
        }
    }
}

@Composable
fun VariableTextField(label: String, state: String, onValueChange: (String) -> Unit) {
    TextField(
        value = state,
        onValueChange = { text ->
            if (text.all { it.isDigit() || it == '.' || it == ',' || it == '-' } || text.isEmpty()) {
                val newText = text.replace(',', '.')
                if (newText.count { it == '-' } <= 1 && (newText.indexOf('-') <= 0 || newText.length == 1)) {
                    onValueChange(newText)
                }
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number, // Для ввода чисел
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.padding(8.dp)
    )
}

// Пример функций для расчетов
fun calculateAC(
    x3: String, x2: String,
    z3: String, z2: String,
    x1: String,
    canvas: String, segment: String): Double {
    val v1 = canvas.toDoubleOrNull() ?: 0.0
    val v2 = segment.toDoubleOrNull() ?: 0.0
    val v3 = x1.toDoubleOrNull() ?: 0.0
    val v4 = x2.toDoubleOrNull() ?: 0.0
    return calculateH(x2, x3, z2, z3) - (((v4 - v3) / 2.0) - (v1 / 2.0) + ((v2 - v1) / 2.0))
}

fun calculateH(
    x2: String, x3: String,
    z2: String, z3: String): Double {
    val v1 = x2.toDoubleOrNull() ?: 0.0
    val v2 = x3.toDoubleOrNull() ?: 0.0
    val v3 = z2.toDoubleOrNull() ?: 0.0
    val v4 = z3.toDoubleOrNull() ?: 0.0
    return (abs(v2 - v1) - abs(v4 - v3)) / 2.0
}

fun calculateZ(
    x3: String, x2: String,
    z2: String, z3: String,
    diametr: String): Double {
    val v1 = x3.toDoubleOrNull() ?: 0.0
    val v2 = x2.toDoubleOrNull() ?: 0.0
    val v3 = z2.toDoubleOrNull() ?: 0.0
    val v4 = z3.toDoubleOrNull() ?: 0.0
    val v5 = diametr.toDoubleOrNull() ?: 0.0
    return abs(v1 - v2) - (v5 / 2.0) - (abs(v1 - v2) - abs(v4 - v3)) / 2.0
}