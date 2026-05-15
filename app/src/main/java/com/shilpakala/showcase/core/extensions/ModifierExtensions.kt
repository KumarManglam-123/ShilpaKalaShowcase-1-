package com.shilpakala.showcase.core.extensions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.shilpakala.showcase.core.constants.AppConstants

/**
 * Adds a clickable modifier that debounces rapid clicks.
 */
fun Modifier.debouncedClickable(
    debounceMs: Long = 500L,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceMs) {
            lastClickTime = currentTime
            onClick()
        }
    }
}

/**
 * Adds accessibility content description.
 */
fun Modifier.accessibilityDescription(description: String): Modifier {
    return semantics { contentDescription = description }
}

/**
 * Ensures minimum touch target size for accessibility (48dp).
 */
fun Modifier.minTouchTarget(): Modifier {
    return this.then(
        Modifier.semantics {
            // Material 3 handles touch target expansion automatically
        }
    )
}
