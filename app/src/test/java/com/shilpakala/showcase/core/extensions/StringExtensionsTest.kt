package com.shilpakala.showcase.core.extensions

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `sanitize removes harmful characters`() {
        assertThat("<script>alert('xss')</script>".sanitize()).isEqualTo("scriptalert(xss)/script")
    }

    @Test
    fun `sanitize trims whitespace`() {
        assertThat("  hello  ".sanitize()).isEqualTo("hello")
    }

    @Test
    fun `isValidName accepts valid names`() {
        assertThat("Ravi Kumar".isValidName()).isTrue()
        assertThat("ರವಿ ಕುಮಾರ".isValidName()).isTrue()
        assertThat("O'Brien".isValidName()).isTrue()
    }

    @Test
    fun `isValidName rejects invalid names`() {
        assertThat("".isValidName()).isFalse()
        assertThat("A".isValidName()).isFalse()
        assertThat("123".isValidName()).isFalse()
    }

    @Test
    fun `ellipsize truncates long strings`() {
        assertThat("Hello World".ellipsize(8)).isEqualTo("Hello W…")
        assertThat("Short".ellipsize(10)).isEqualTo("Short")
    }

    @Test
    fun `toTitleCase works correctly`() {
        assertThat("hello world".toTitleCase()).isEqualTo("Hello World")
    }
}
