package com.example.module_3.cryptography

import org.junit.Test

class CalculatePasswordStrengthTest {

    @Test
    fun `weak password is classified as weak`() {
        val strength = calculatePasswordStrength("abc")
        assert(strength == PasswordStrength.WEAK)
    }

    @Test
    fun `medium password is classified as medium`() {
        val strength = calculatePasswordStrength("Abcdef1")
        assert(strength == PasswordStrength.MEDIUM)
    }

    @Test
    fun `strong password is classified as strong`() {
        val strength = calculatePasswordStrength("Abcdef1!")
        assert(strength == PasswordStrength.STRONG)
    }

}