package com.example.com.example.module_2

import com.example.module_2.RemoveChars
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RemoveCharsTest {

    @Test
    fun `removes vowels from sentence`() {
        val result = RemoveChars.removeChars(
            "hello world",
            setOf('o', 'u')
        )

        assertEquals("hell wrld", result)
    }

    @Test
    fun `handles empty string`() {
        val result = RemoveChars.removeChars(
            "",
            setOf('a')
        )

        assertEquals("", result)
    }

    @Test
    fun `removes all characters`() {
        val result = RemoveChars.removeChars(
            "aabbcc",
            setOf('a', 'b', 'c')
        )

        assertEquals("", result)
    }

    @Test
    fun `returns unchanged string when set is empty`() {
        val result = RemoveChars.removeChars(
            "abc",
            emptySet()
        )

        assertEquals("abc", result)
    }

    @Test
    fun `handles accented characters`() {
        val result = RemoveChars.removeChars(
            "canción",
            setOf('ó')
        )

        assertEquals("cancin", result)
    }

    @Test
    fun `handles symbols`() {
        val result = RemoveChars.removeChars(
            "a!b@c#d$",
            setOf('!', '@', '#', '$')
        )

        assertEquals("abcd", result)
    }

    @Test
    fun `handles spaces`() {
        val result = RemoveChars.removeChars(
            "h e l l o",
            setOf(' ')
        )

        assertEquals("hello", result)
    }

    @Test
    fun `is case sensitive`() {
        val result = RemoveChars.removeChars(
            "Hello World",
            setOf('h', 'o')
        )

        assertEquals("Hell Wrld", result)
    }

    @Test
    fun `does not remove characters not present`() {
        val result = RemoveChars.removeChars(
            "kotlin",
            setOf('x', 'y', 'z')
        )

        assertEquals("kotlin", result)
    }

    @Test
    fun `handles repeated characters`() {
        val result = RemoveChars.removeChars(
            "aaaaabbbbb",
            setOf('a')
        )

        assertEquals("bbbbb", result)
    }

    @Test
    fun `handles newline and tabs`() {
        val result = RemoveChars.removeChars(
            "hello\nworld\t!",
            setOf('\n', '\t')
        )

        assertEquals("helloworld!", result)
    }

    @Test
    fun `handles large input efficiently`() {
        val largeInput = buildString {
            repeat(100_000) {
                append("abc")
            }
        }

        val result = RemoveChars.removeChars(
            largeInput,
            setOf('b')
        )

        assertEquals(200_000, result.length)
    }
}