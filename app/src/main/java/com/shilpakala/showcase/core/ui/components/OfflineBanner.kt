package com.shilpakala.showcase.core.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OfflineBanner(isVisible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.WifiOff,
                contentDescription = "Offline",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Offline Mode",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}
