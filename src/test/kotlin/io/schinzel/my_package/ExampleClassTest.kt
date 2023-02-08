package io.schinzel.my_package

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExampleClassTest {

    @Test
    fun `doubleIt _ 4 _ 8`() {
        val actual = ExampleClass().doubleIt(4)
        val expected = 8
        assertThat(actual).isEqualTo(expected)
    }
}