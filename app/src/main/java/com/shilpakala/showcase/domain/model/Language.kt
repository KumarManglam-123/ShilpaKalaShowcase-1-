package com.shilpakala.showcase.domain.model

enum class Language(val code: String, val displayName: String) {
    ENGLISH("en", "English"),
    KANNADA("kn", "ಕನ್ನಡ");

    companion object {
        fun fromCode(code: String): Language {
            return entries.firstOrNull { it.code == code } ?: ENGLISH
        }
    }
}
