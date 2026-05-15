package com.shilpakala.showcase.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {

    private val displayDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val displayDateTimeFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    private val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun currentTimeMillis(): Long = System.currentTimeMillis()

    fun formatDisplayDate(timestamp: Long): String {
        return displayDateFormat.format(Date(timestamp))
    }

    fun formatDisplayDateTime(timestamp: Long): String {
        return displayDateTimeFormat.format(Date(timestamp))
    }

    fun formatIso(timestamp: Long): String {
        return isoFormat.format(Date(timestamp))
    }

    fun getRelativeTimeString(timestamp: Long): String {
        val now = currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60_000 -> "Just now"
            diff < 3_600_000 -> "${diff / 60_000}m ago"
            diff < 86_400_000 -> "${diff / 3_600_000}h ago"
            diff < 604_800_000 -> "${diff / 86_400_000}d ago"
            else -> formatDisplayDate(timestamp)
        }
    }
}
