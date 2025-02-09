package ru.itis.homework6.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import ru.itis.homework6.R

@Composable
fun DurationTextField(duration: String, onDurationChange: (String) -> Unit) {
    OutlinedTextField(
        value = duration,
        onValueChange = { input ->
            val cleanInput = input.filter { it.isDigit() }
            val formatted = when {
                cleanInput.length <= 2 -> cleanInput
                cleanInput.length in 3..4 -> "${cleanInput.dropLast(2)}:${cleanInput.takeLast(2)}" // мин:сек
                else -> "${cleanInput.dropLast(2)}:${cleanInput.takeLast(2)}"
            }
            onDurationChange(formatted)
        },
        label = { Text(stringResource(id = R.string.duration)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        visualTransformation = { text -> TransformedText(text, OffsetMapping.Identity) },
    )
}