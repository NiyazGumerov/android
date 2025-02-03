package ru.itis.homework5

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework5.ui.theme.HomeWork5Theme

@Composable
fun MyScreen() {
    var canceledCoroutines by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var textFieldCoroutineCount by remember { mutableStateOf("") }
    var selectedRadioLaunch by remember { mutableIntStateOf(0) }
    var selectedRadioLogic by remember { mutableIntStateOf(0) }
    var expandedMenu by remember { mutableStateOf(false) }
    val dropdownOptions = listOf(
        stringResource(R.string.select_the_thread_pool_variant_first),
        stringResource(R.string.select_the_thread_pool_variant_second),
        stringResource(R.string.select_the_thread_pool_variant_third),
        stringResource(R.string.select_the_thread_pool_variant_fourth)
    )
    var selectedDispatcher by remember { mutableStateOf(dropdownOptions[0]) }
    var isTextFieldError by remember { mutableStateOf(false) }
    val coroutineScopeDefault = CoroutineScope(Dispatchers.Default)

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                if (selectedRadioLogic == 0) {
                    coroutineScopeDefault.coroutineContext.cancelChildren()
                }
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textFieldCoroutineCount,
            onValueChange = {
                textFieldCoroutineCount = it
                isTextFieldError = false
            },
            label = { Text(stringResource(R.string.courutines_count)) },
            isError = isTextFieldError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Text(stringResource(R.string.how_to_launch_coroutines_question))
        RadioGroup(
            options = listOf(
                stringResource(R.string.how_to_launch_coroutines_variant_first),
                stringResource(R.string.how_to_launch_coroutines_variant_second)
            ), selected = selectedRadioLaunch
        ) {
            selectedRadioLaunch = it
        }

        Text(stringResource(R.string.logic_of_coroutines_question))
        RadioGroup(
            options = listOf(
                stringResource(R.string.logic_of_coroutines_variant_first),
                stringResource(R.string.logic_of_coroutines_variant_second)
            ), selected = selectedRadioLogic
        ) {
            selectedRadioLogic = it
        }

        Text(stringResource(R.string.select_the_thread_pool_question))
        Box {

            Button(onClick = { expandedMenu = true }) {
                Text(selectedDispatcher)
            }

            DropdownMenu(expanded = expandedMenu, onDismissRequest = { expandedMenu = false }) {
                dropdownOptions.forEach { item ->
                    DropdownMenuItem(text = { Text(item) }, onClick = {
                        selectedDispatcher = item
                        expandedMenu = false
                    })
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (textFieldCoroutineCount.isBlank()
                    || textFieldCoroutineCount.toIntOrNull() == null
                    || textFieldCoroutineCount.toInt() < 1
                ) {
                    isTextFieldError = true
                } else {
                    isTextFieldError = false
                    val count = textFieldCoroutineCount.toInt()
                    val dispatcher = when (selectedDispatcher) {
                        dropdownOptions[0] -> Dispatchers.Main
                        dropdownOptions[1] -> Dispatchers.Unconfined
                        dropdownOptions[2] -> Dispatchers.IO
                        else -> Dispatchers.Default
                    }
                    try {
                        when (selectedRadioLaunch) {
                            0 -> {
                                canceledCoroutines = count
                                coroutineScopeDefault.launch {
                                    repeat(count) {
                                        launch(dispatcher) {
                                            delay(1000L)
                                            println("TEST TAG - Count: $it")
                                        }.join()
                                        canceledCoroutines--
                                    }
                                }
                            }

                            1 -> {
                                repeat(count) {
                                    coroutineScopeDefault.launch {
                                        withContext(dispatcher) {
                                            delay(1000L)
                                            println("TEST TAG - Count: $it")
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text(stringResource(id = R.string.start_coroutines_btn))
            }
            Button(onClick = {
                val childrenCount = when (selectedRadioLaunch) {
                    0 -> canceledCoroutines
                    1 -> coroutineScopeDefault.coroutineContext[Job]?.children?.count() ?: 0
                    else -> 0
                }
                if (childrenCount > 0) {
                    coroutineScopeDefault.coroutineContext.cancelChildren()
                    Toast.makeText(context,context.getString(R.string.canceled, childrenCount), Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(context, context.getString(R.string.no_active_coroutines), Toast.LENGTH_LONG)
                        .show()
                }
            }) {
                Text(stringResource(id = R.string.cancel_coroutines_btn))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    HomeWork5Theme {
        MyScreen()
    }
}


