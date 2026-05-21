package com.example.module_3.cryptography

enum class PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}

fun calculatePasswordStrength(password: String): PasswordStrength {

    var score = 0

    if (password.length >= 8) score++
    if (password.length >= 12) score++

    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++

    return when {
        score <= 2 -> PasswordStrength.WEAK
        score <= 4 -> PasswordStrength.MEDIUM
        else -> PasswordStrength.STRONG
    }
}