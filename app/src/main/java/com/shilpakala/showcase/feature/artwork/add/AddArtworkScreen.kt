package com.shilpakala.showcase.feature.artwork.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.shilpakala.showcase.core.ui.components.GoldButton
import com.shilpakala.showcase.domain.model.ArtworkStatus

private val materials = listOf("Granite", "Soapstone", "Sandstone", "Marble", "Teak Wood", "Rosewood", "Sandalwood", "Ebony")

@Composable
fun AddArtworkScreen(viewModel: AddArtworkViewModel, onNavigateBack: () -> Unit, onArtworkAdded: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        if (uris.isNotEmpty()) viewModel.addImages(uris, context.cacheDir)
    }

    LaunchedEffect(uiState.isSaved) { if (uiState.isSaved) onArtworkAdded() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_artwork)) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
            // Images
            Text(stringResource(R.string.upload_images), style = MaterialTheme.typography.titleMedium)
            Text(stringResource(R.string.images_count, uiState.imageUris.size), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Card(
                        modifier = Modifier.size(100.dp).clickable { launcher.launch("image/*") },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(Modifier.fillMaxSize(), Alignment.Center) { Icon(Icons.Default.AddPhotoAlternate, null, tint = MaterialTheme.colorScheme.primary) }
                    }
                }
                itemsIndexed(uiState.imageUris) { index, uri ->
                    Box {
                        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                        IconButton(onClick = { viewModel.removeImage(index) }, modifier = Modifier.align(Alignment.TopEnd).size(24.dp)) {
                            Icon(Icons.Default.Close, "Remove", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            OutlinedTextField(value = uiState.title, onValueChange = { viewModel.updateTitle(it) }, label = { Text(stringResource(R.string.artwork_title)) }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))

            // Material dropdown
            var materialExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = materialExpanded, onExpandedChange = { materialExpanded = it }) {
                OutlinedTextField(value = uiState.material, onValueChange = {}, readOnly = true, label = { Text(stringResource(R.string.material)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = materialExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor())
                ExposedDropdownMenu(expanded = materialExpanded, onDismissRequest = { materialExpanded = false }) {
                    materials.forEach { m ->
                        DropdownMenuItem(text = { Text(m) }, onClick = { viewModel.updateMaterial(m); materialExpanded = false })
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = uiState.dimensions, onValueChange = { viewModel.updateDimensions(it) }, label = { Text(stringResource(R.string.dimensions)) }, placeholder = { Text("e.g., 24\" x 12\" x 8\"") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = uiState.priceRange, onValueChange = { viewModel.updatePriceRange(it) }, label = { Text(stringResource(R.string.price_range)) }, placeholder = { Text("e.g., ₹15,000 - ₹25,000") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))

            // Status chips
            Text(stringResource(R.string.status), style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArtworkStatus.entries.forEach { status ->
                    FilterChip(selected = uiState.status == status, onClick = { viewModel.updateStatus(status) }, label = { Text(status.displayName) })
                }
            }

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = uiState.description, onValueChange = { viewModel.updateDescription(it) }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth().height(120.dp), maxLines = 5)

            if (uiState.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(uiState.error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(24.dp))
            GoldButton(text = stringResource(R.string.save_artwork), onClick = { viewModel.save() }, modifier = Modifier.fillMaxWidth(), isLoading = uiState.isLoading)
        }
    }
}
