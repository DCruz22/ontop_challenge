package com.example.module_2

object RemoveChars {
    fun removeChars(input: String, charsToRemove: Set<Char>): String {
        if (input.isEmpty()) return ""

        if (charsToRemove.isEmpty()) return input

        val result = StringBuilder()

        for (char in input) {
            if (!charsToRemove.contains(char)) {
                result.append(char)
            }
        }

        return result.toString()
    }
}