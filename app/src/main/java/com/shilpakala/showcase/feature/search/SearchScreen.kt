package com.shilpakala.showcase.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.ui.components.EmptyState
import com.shilpakala.showcase.core.ui.components.LoadingIndicator
import com.shilpakala.showcase.domain.model.Artwork

@Composable
fun SearchScreen(viewModel: SearchViewModel, onNavigateToDetail: (String) -> Unit, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = uiState.query, onValueChange = { viewModel.updateQuery(it) },
                        placeholder = { Text(stringResource(R.string.search_hint)) },
                        singleLine = true, modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (uiState.query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.updateQuery("") }) { Icon(Icons.Default.Clear, "Clear") }
                            }
                        }
                    )
                },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (uiState.materials.isNotEmpty()) {
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.materials) { material ->
                        FilterChip(
                            selected = uiState.selectedMaterial == material,
                            onClick = { viewModel.selectMaterial(if (uiState.selectedMaterial == material) null else material) },
                            label = { Text(material) }
                        )
                    }
                }
            }

            when {
                uiState.isLoading -> LoadingIndicator()
                uiState.results.isEmpty() && uiState.hasSearched -> EmptyState(title = stringResource(R.string.no_results))
                else -> LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.results, key = { it.id }) { artwork ->
                        SearchResultCard(artwork = artwork, onClick = { onNavigateToDetail(artwork.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultCard(artwork: Artwork, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = MaterialTheme.shapes.medium) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (artwork.imageUris.isNotEmpty()) {
                AsyncImage(model = artwork.imageUris.first(), contentDescription = null, modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                Spacer(Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(artwork.title, style = MaterialTheme.typography.titleSmall)
                Text(artwork.material, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(artwork.status.displayName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
