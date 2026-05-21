package com.example.module_3.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.module_3.R
import com.example.module_3.cryptography.PasswordStrength

@Composable
fun PasswordStrengthIndicator(
    strength: PasswordStrength,
) {

    val strengthText = when (strength) {
        PasswordStrength.WEAK -> stringResource(R.string.strength_weak)
        PasswordStrength.MEDIUM -> stringResource(R.string.strength_medium)
        PasswordStrength.STRONG -> stringResource(R.string.strength_strong)
    }

    Text(
        text = stringResource(R.string.strength_label, strengthText),
        style = MaterialTheme.typography.bodyMedium
    )
}