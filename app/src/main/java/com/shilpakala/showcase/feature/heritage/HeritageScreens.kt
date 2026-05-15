package com.shilpakala.showcase.feature.heritage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.ui.components.EmptyState
import com.shilpakala.showcase.core.ui.components.LoadingIndicator
import com.shilpakala.showcase.domain.model.Heritage

private val heritageEmojis = mapOf(
    "Hoysala" to "🏛️", "Dravidian" to "⛩️", "Vijayanagara" to "🏰",
    "Folk" to "🎭", "Channapatna" to "🪆"
)

@Composable
fun HeritageListScreen(viewModel: HeritageViewModel, onNavigateToDetail: (String) -> Unit, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.heritage_stories)) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingIndicator(Modifier.padding(padding))
            uiState.stories.isEmpty() -> EmptyState(modifier = Modifier.padding(padding), title = "No heritage stories")
            else -> LazyColumn(modifier = Modifier.padding(padding), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.stories, key = { it.id }) { story ->
                    HeritageCard(story = story, onClick = { onNavigateToDetail(story.id) })
                }
            }
        }
    }
}

@Composable
private fun HeritageCard(story: Heritage, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(heritageEmojis[story.style] ?: "🎨", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(12.dp))
            Text(story.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
            Spacer(Modifier.height(8.dp))
            Text(story.narrative.take(150) + if (story.narrative.length > 150) "…" else "", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (story.isAiGenerated) {
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.ai_generated_label), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
fun HeritageDetailScreen(viewModel: HeritageViewModel, heritageId: String, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(heritageId) { viewModel.selectStory(heritageId) }

    val story = uiState.selectedStory

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(story?.title ?: "") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        if (story == null) {
            LoadingIndicator(Modifier.padding(padding))
            return@Scaffold
        }
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize(), contentPadding = PaddingValues(24.dp)) {
            item {
                Text(heritageEmojis[story.style] ?: "🎨", style = MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(16.dp))
                Text(story.title, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(24.dp))
                Text(story.narrative, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface, lineHeight = MaterialTheme.typography.bodyLarge.lineHeight)
                if (story.isAiGenerated) {
                    Spacer(Modifier.height(24.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                        Text(stringResource(R.string.ai_generated_label), modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchedEffect(heritageId: String, block: suspend () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(heritageId) { block() }
}
