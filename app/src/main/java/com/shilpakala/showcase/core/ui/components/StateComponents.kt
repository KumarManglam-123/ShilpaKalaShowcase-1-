package com.shilpakala.showcase.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.SearchOff,
    title: String = "Nothing here yet",
    subtitle: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon, contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle, style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), textAlign = TextAlign.Center
            )
        }
        if (actionLabel != null && onAction != null) {
            Spacer(modifier = Modifier.height(24.dp))
            GoldButton(text = actionLabel, onClick = onAction)
        }
    }
}

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    message: String = "Something went wrong",
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "⚠️", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center
        )
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(onClick = onRetry) { Text("Retry") }
        }
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}
