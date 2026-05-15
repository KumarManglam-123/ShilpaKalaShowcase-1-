package com.shilpakala.showcase.feature.artwork.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun ArtworkListScreen(
    viewModel: ArtworkListViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAdd: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_portfolio)) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAdd(uiState.shilpiId) },
                containerColor = MaterialTheme.colorScheme.primary
            ) { Icon(Icons.Default.Add, stringResource(R.string.cd_add)) }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingIndicator(Modifier.padding(padding))
            uiState.artworks.isEmpty() -> EmptyState(
                modifier = Modifier.padding(padding).fillMaxSize(),
                title = "No artworks yet",
                subtitle = "Add your first artwork to build your portfolio",
                actionLabel = stringResource(R.string.add_artwork),
                onAction = { onNavigateToAdd(uiState.shilpiId) }
            )
            else -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp
                ) {
                    items(uiState.artworks, key = { it.id }) { artwork ->
                        ArtworkGridCard(artwork = artwork, onClick = { onNavigateToDetail(artwork.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtworkGridCard(artwork: Artwork, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            if (artwork.imageUris.isNotEmpty()) {
                AsyncImage(
                    model = artwork.imageUris.first(), contentDescription = artwork.title,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp, max = 240.dp).clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(artwork.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
                Text(artwork.material, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (artwork.priceRange.isNotBlank()) {
                    Text(artwork.priceRange, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
