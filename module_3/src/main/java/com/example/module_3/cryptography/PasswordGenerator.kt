package com.example.module_3.cryptography

import java.security.SecureRandom

object PasswordGenerator {
    private val secureRandom = SecureRandom()

    private const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    private const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val NUMBERS = "0123456789"
    private const val SYMBOLS = "!@#$%^&*()-_=+[]{}<>?/"

    fun generatePassword(
        length: Int,
        useUppercase: Boolean = true,
        useNumbers: Boolean = true,
        useSymbols: Boolean = true
    ): String {

        require(length >= 4) {
            throw IllegalArgumentException("Password length must be at least 4")
        }

        val requiredChars = mutableListOf<Char>()
        val characterPool = StringBuilder(LOWERCASE)

        if (useUppercase) {
            characterPool.append(UPPERCASE)
            requiredChars.add(randomCharFrom(UPPERCASE))
        }

        if (useNumbers) {
            characterPool.append(NUMBERS)
            requiredChars.add(randomCharFrom(NUMBERS))
        }

        if (useSymbols) {
            characterPool.append(SYMBOLS)
            requiredChars.add(randomCharFrom(SYMBOLS))
        }

        requiredChars.add(randomCharFrom(LOWERCASE))

        val password = StringBuilder()

        for (char in requiredChars) {
            password.append(char)
        }

        while (password.length < length) {
            password.append(
                randomCharFrom(characterPool.toString())
            )
        }

        return secureShuffle(password.toString())
    }

    private fun randomCharFrom(source: String): Char {
        val index = secureRandom.nextInt(source.length)
        return source[index]
    }

    private fun secureShuffle(input: String): String {
        val chars = input.toCharArray()

        for (i in chars.indices.reversed()) {
            val j = secureRandom.nextInt(i + 1)

            val temp = chars[i]
            chars[i] = chars[j]
            chars[j] = temp
        }

        return String(chars)
    }
}