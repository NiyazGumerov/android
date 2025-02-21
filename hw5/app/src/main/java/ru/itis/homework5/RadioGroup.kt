package ru.itis.homework5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioGroup(options: List<String>, selected: Int, onOptionSelected: (Int) -> Unit) {
    Column {
        options.forEachIndexed() { index, text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = (index == selected), onClick = { onOptionSelected(index) })
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}