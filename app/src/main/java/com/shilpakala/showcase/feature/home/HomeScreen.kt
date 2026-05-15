package com.shilpakala.showcase.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.ui.components.*
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.Shilpi

private val categories = listOf("Stone", "Wood", "Bronze", "Marble", "Terracotta", "Sandstone")

@Composable
fun HomeScreen(
    viewModel: HomeViewModel, isOnline: Boolean,
    onNavigateToArtworkList: (String) -> Unit, onNavigateToArtworkDetail: (String) -> Unit,
    onNavigateToHeritage: () -> Unit, onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit, onNavigateToAddArtwork: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).statusBarsPadding()) {
        OfflineBanner(isVisible = !isOnline)

        PullToRefreshBox(isRefreshing = uiState.isRefreshing, onRefresh = { viewModel.refresh() }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Shilpa-Kala", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light, letterSpacing = 2.sp), color = MaterialTheme.colorScheme.primary)
                            Text("SHOWCASE", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 4.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row {
                            IconButton(onClick = onNavigateToSearch) { Icon(Icons.Outlined.Search, stringResource(R.string.cd_search)) }
                            IconButton(onClick = onNavigateToSettings) { Icon(Icons.Outlined.Settings, stringResource(R.string.cd_settings)) }
                        }
                    }
                }

                // Search bar
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).clickable { onNavigateToSearch() },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Search, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.width(12.dp))
                            Text(stringResource(R.string.search_artworks), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }

                // Categories
                item {
                    Text(stringResource(R.string.categories), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(12.dp))
                    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categories) { cat ->
                            FilterChip(selected = false, onClick = { onNavigateToSearch() }, label = { Text(cat) })
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }

                // Featured artisan
                uiState.currentShilpi?.let { shilpi ->
                    item {
                        Text(stringResource(R.string.featured_artisan), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(12.dp))
                        FeaturedArtisanCard(
                            shilpi = shilpi,
                            onClick = { onNavigateToArtworkList(shilpi.id) },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(Modifier.height(24.dp))
                    }
                }

                // Quick actions
                uiState.currentShilpi?.let { shilpi ->
                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            QuickActionCard(icon = Icons.Default.Add, label = "Add Artwork", modifier = Modifier.weight(1f), onClick = { onNavigateToAddArtwork(shilpi.id) })
                            QuickActionCard(icon = Icons.Default.Collections, label = "Portfolio", modifier = Modifier.weight(1f), onClick = { onNavigateToArtworkList(shilpi.id) })
                            QuickActionCard(icon = Icons.Default.HistoryEdu, label = "Heritage", modifier = Modifier.weight(1f), onClick = onNavigateToHeritage)
                        }
                        Spacer(Modifier.height(24.dp))
                    }
                }

                // Recent artworks
                if (uiState.recentArtworks.isNotEmpty()) {
                    item {
                        Text(stringResource(R.string.recently_added), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(12.dp))
                    }
                    items(uiState.recentArtworks, key = { it.id }) { artwork ->
                        RecentArtworkCard(artwork = artwork, onClick = { onNavigateToArtworkDetail(artwork.id) }, modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp))
                    }
                }

                if (uiState.isLoading) {
                    item { LoadingIndicator() }
                }

                if (!uiState.isLoading && uiState.recentArtworks.isEmpty() && uiState.currentShilpi == null) {
                    item { EmptyState(title = "Welcome!", subtitle = "Set up your profile to get started") }
                }
            }
        }
    }
}

@Composable
private fun FeaturedArtisanCard(shilpi: Shilpi, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (shilpi.profilePhotoUri != null) {
                    AsyncImage(model = shilpi.profilePhotoUri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    Text(shilpi.name.take(1).uppercase(), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(shilpi.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text("${shilpi.village}, ${shilpi.district}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
                Text(shilpi.specialisation, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun QuickActionCard(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun RecentArtworkCard(artwork: Artwork, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (artwork.imageUris.isNotEmpty()) {
                AsyncImage(
                    model = artwork.imageUris.first(), contentDescription = null,
                    modifier = Modifier.size(72.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(artwork.title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Text(artwork.material, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(artwork.status.displayName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
            if (artwork.priceRange.isNotBlank()) {
                Text(artwork.priceRange, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
