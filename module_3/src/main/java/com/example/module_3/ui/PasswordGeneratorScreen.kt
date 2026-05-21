package com.example.module_3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.module_3.R
import com.example.module_3.cryptography.PasswordGenerator
import com.example.module_3.cryptography.calculatePasswordStrength
import com.example.module_3.ui.components.PasswordSettingSwitch
import com.example.module_3.ui.components.PasswordStrengthIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGeneratorScreen() {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    var password by remember { mutableStateOf("") }

    var length by remember { mutableFloatStateOf(12f) }
    var useUppercase by remember { mutableStateOf(true) }
    var useNumbers by remember { mutableStateOf(true) }
    var useSymbols by remember { mutableStateOf(true) }

    val passwordStrength = remember(password) {
        calculatePasswordStrength(password)
    }

    val invalidInputMessage = stringResource(R.string.invalid_input)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = stringResource(R.string.password_generator_title))
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Column {
                Text(text = stringResource(R.string.password_length, length.toInt()))

                Slider(
                    value = length,
                    onValueChange = { length = it },
                    valueRange = 2f..32f,
                    steps = 27
                )
            }

            PasswordSettingSwitch(
                title = stringResource(R.string.use_uppercase),
                checked = useUppercase,
                icon = Icons.Default.TextFields,
                onCheckedChange = {
                    useUppercase = it
                }
            )

            PasswordSettingSwitch(
                title = stringResource(R.string.use_numbers),
                checked = useNumbers,
                icon = Icons.AutoMirrored.Filled.List,
                onCheckedChange = {
                    useNumbers = it
                }
            )

            PasswordSettingSwitch(
                title = stringResource(R.string.use_symbols),
                checked = useSymbols,
                icon = Icons.Default.EmojiSymbols,
                onCheckedChange = {
                    useSymbols = it
                }
            )

            Button(
                onClick = {
                    try {
                        password = PasswordGenerator.generatePassword(
                            length = length.toInt(),
                            useUppercase = useUppercase,
                            useNumbers = useNumbers,
                            useSymbols = useSymbols
                        )
                    } catch (e: IllegalArgumentException) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = e.message ?: invalidInputMessage
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.generate_password))
            }

            if (password.isNotEmpty()) {

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = stringResource(R.string.generated_password),
                            style = MaterialTheme.typography.titleMedium
                        )

                        SelectionContainer {
                            Text(
                                text = password,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        PasswordStrengthIndicator(passwordStrength)

                        OutlinedButton(
                            onClick = {
                                clipboardManager.setText(
                                    AnnotatedString(password)
                                )
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = stringResource(R.string.copy_password)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(stringResource(R.string.copy))
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PasswordGeneratorScreenPreview() {
    PasswordGeneratorScreen()
}
