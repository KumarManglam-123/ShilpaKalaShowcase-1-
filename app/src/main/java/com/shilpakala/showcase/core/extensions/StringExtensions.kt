package com.shilpakala.showcase.core.extensions

/**
 * Sanitizes user input by trimming whitespace and removing potentially harmful characters.
 */
fun String.sanitize(): String {
    return this.trim()
        .replace(Regex("[<>\"'&]"), "")
        .take(500) // Prevent extremely long inputs
}

/**
 * Validates that a name contains only valid characters (letters, spaces, periods, hyphens).
 */
fun String.isValidName(): Boolean {
    if (isBlank()) return false
    if (length < 2 || length > 100) return false
    return matches(Regex("^[\\p{L}\\s.'-]+$"))
}

/**
 * Validates that a village/district name is reasonable.
 */
fun String.isValidLocation(): Boolean {
    if (isBlank()) return false
    if (length < 2 || length > 100) return false
    return matches(Regex("^[\\p{L}\\s.,'()-]+$"))
}

/**
 * Truncates string with ellipsis.
 */
fun String.ellipsize(maxLength: Int): String {
    return if (length > maxLength) {
        take(maxLength - 1) + "…"
    } else {
        this
    }
}

/**
 * Converts string to title case.
 */
fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}
