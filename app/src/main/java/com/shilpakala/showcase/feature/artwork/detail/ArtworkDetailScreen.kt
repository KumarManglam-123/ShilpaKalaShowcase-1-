package com.shilpakala.showcase.feature.artwork.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.core.extensions.isPackageInstalled
import com.shilpakala.showcase.core.extensions.showToast
import com.shilpakala.showcase.core.ui.components.GoldButton
import com.shilpakala.showcase.core.ui.components.LoadingIndicator

@Composable
fun ArtworkDetailScreen(
    viewModel: ArtworkDetailViewModel,
    onNavigateToImageViewer: (String) -> Unit,
    onNavigateToWipTimeline: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.artwork?.title ?: "") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingIndicator(Modifier.padding(padding))
            return@Scaffold
        }
        val artwork = uiState.artwork ?: return@Scaffold

        Column(modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState())) {
            // Image gallery
            if (artwork.imageUris.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(artwork.imageUris) { _, uri ->
                        AsyncImage(
                            model = uri, contentDescription = artwork.title,
                            modifier = Modifier.size(280.dp).clip(RoundedCornerShape(12.dp)).clickable { onNavigateToImageViewer(uri) },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(artwork.title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(4.dp))
                Text("ID: ${artwork.id}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(16.dp))

                DetailRow("Material", artwork.material)
                DetailRow("Dimensions", artwork.dimensions)
                DetailRow("Price Range", artwork.priceRange)
                DetailRow("Status", artwork.status.displayName)

                if (artwork.description.isNotBlank()) {
                    Spacer(Modifier.height(16.dp))
                    Text(artwork.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(Modifier.height(24.dp))

                // WIP Timeline button
                OutlinedButton(onClick = { onNavigateToWipTimeline(artwork.id) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Timeline, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.work_in_progress))
                }

                Spacer(Modifier.height(12.dp))

                // Inquiry button
                GoldButton(
                    text = stringResource(R.string.inquire_now),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { handleInquiry(context, artwork.id) }
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    if (value.isBlank()) return
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.width(120.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
    }
}

private fun handleInquiry(context: Context, productId: String) {
    val message = "Hello, I am interested in [Product ID: $productId]"
    val encodedMessage = Uri.encode(message)

    // Try WhatsApp first
    if (context.isPackageInstalled(AppConstants.WHATSAPP_PACKAGE) || context.isPackageInstalled(AppConstants.WHATSAPP_BUSINESS_PACKAGE)) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/?text=$encodedMessage"))
            context.startActivity(intent)
            return
        } catch (_: Exception) {}
    }

    // Fallback to SMS
    try {
        val smsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:?body=$encodedMessage"))
        if (smsIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(smsIntent)
            return
        }
    } catch (_: Exception) {}

    // Fallback to share sheet
    try {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    } catch (e: Exception) {
        context.showToast("No messaging app found")
    }
}
