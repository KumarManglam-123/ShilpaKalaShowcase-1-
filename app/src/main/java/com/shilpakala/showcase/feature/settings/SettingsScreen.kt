package com.shilpakala.showcase.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shilpakala.showcase.R
import com.shilpakala.showcase.domain.model.Language

@Composable
fun SettingsScreen(viewModel: SettingsViewModel, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)
        ) {
            // Language
            Text(stringResource(R.string.language), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Language.entries.forEach { lang ->
                    FilterChip(
                        selected = uiState.languageCode == lang.code,
                        onClick = { viewModel.setLanguage(lang) },
                        label = { Text(lang.displayName) }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Dark Mode
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Switch(checked = uiState.isDarkMode, onCheckedChange = { viewModel.setDarkMode(it) })
            }

            Spacer(Modifier.height(24.dp))

            // Font Scale
            Text(stringResource(R.string.font_size), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Slider(
                value = uiState.fontScale, onValueChange = { viewModel.setFontScale(it) },
                valueRange = 0.8f..2.0f, steps = 5
            )
            Text("${(uiState.fontScale * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(24.dp))
            Divider()
            Spacer(Modifier.height(24.dp))

            // Clear Cache
            OutlinedButton(onClick = { viewModel.clearCache() }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.CleaningServices, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.clear_cache))
            }

            if (uiState.cacheCleared) {
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.cache_cleared), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            // Export Data
            OutlinedButton(onClick = { viewModel.exportData() }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Download, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.export_data))
            }

            Spacer(Modifier.height(32.dp))

            // About
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.about), style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Shilpa-Kala Showcase", style = MaterialTheme.typography.bodyMedium)
                    Text(stringResource(R.string.app_version, uiState.appVersion), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(8.dp))
                    Text("A premium digital gallery platform for traditional Indian artisans.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
