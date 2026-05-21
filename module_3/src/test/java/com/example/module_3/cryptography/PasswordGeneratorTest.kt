package com.example.module_3.cryptography

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PasswordGeneratorTest {

    @Test
    fun `password length matches requested length`() {
        val password = PasswordGenerator.generatePassword(12)

        assertEquals(12, password.length)
    }

    @Test
    fun `password contains uppercase when enabled`() {
        val password = PasswordGenerator.generatePassword(
            length = 12,
            useUppercase = true,
            useNumbers = false,
            useSymbols = false
        )

        assertTrue(password.any { it.isUpperCase() })
        assertTrue(password.all { it.isLetterOrDigit() })
        assertFalse(password.any { it.isDigit() })
    }

    @Test
    fun `password contains number when enabled`() {
        val password = PasswordGenerator.generatePassword(
            length = 12,
            useUppercase = false,
            useNumbers = true,
            useSymbols = false
        )

        assertTrue(password.any { it.isDigit() })
        assertTrue(password.all { it.isLetterOrDigit() })
        assertFalse(password.any { it.isUpperCase() })
    }

    @Test
    fun `password contains symbol when enabled`() {
        val password = PasswordGenerator.generatePassword(
            length = 12,
            useUppercase = false,
            useNumbers = false,
            useSymbols = true
        )

        assertTrue(password.any { !it.isLetterOrDigit() })
        assertFalse(password.any { it.isUpperCase() })
        assertFalse(password.any { it.isDigit() })
    }

    @Test
    fun `password contains lowercase letters`() {
        val password = PasswordGenerator.generatePassword(12)

        assertTrue(password.any { it.isLowerCase() })
    }

    @Test
    fun `two consecutive passwords should differ`() {
        val password1 = PasswordGenerator.generatePassword(16)
        val password2 = PasswordGenerator.generatePassword(16)

        assertNotEquals(password1, password2)
    }

    @Test
    fun `throws exception when length is less than 4`() {
        assertThrows(IllegalArgumentException::class.java) {
            PasswordGenerator.generatePassword(2)
        }
    }

    @Test
    fun `works with only lowercase characters`() {
        val password = PasswordGenerator.generatePassword(
            length = 10,
            useUppercase = false,
            useNumbers = false,
            useSymbols = false
        )

        assertEquals(10, password.length)
        assertTrue(password.all { it.isLowerCase() })
    }

    @Test
    fun `works with uppercase and numbers only`() {
        val password = PasswordGenerator.generatePassword(
            length = 15,
            useUppercase = true,
            useNumbers = true,
            useSymbols = false
        )

        assertEquals(15, password.length)
        assertTrue(password.any { it.isUpperCase() })
        assertTrue(password.any { it.isDigit() })
        assertTrue(password.all { it.isLetterOrDigit() })
    }

    @Test
    fun `generated passwords are highly unique`() {
        val generated = mutableSetOf<String>()

        repeat(1000) {
            generated.add(
                PasswordGenerator.generatePassword(16)
            )
        }

        assertEquals(1000, generated.size)
    }
}
