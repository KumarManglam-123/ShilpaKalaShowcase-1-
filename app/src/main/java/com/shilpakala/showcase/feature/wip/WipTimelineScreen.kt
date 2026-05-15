package com.shilpakala.showcase.feature.wip

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.shilpakala.showcase.core.ui.components.*
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.domain.model.WipStageName

@Composable
fun WipTimelineScreen(viewModel: WipViewModel, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.work_in_progress)) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.cd_back)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showAddDialog() }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, stringResource(R.string.add_stage))
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Horizontal stage indicators
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(WipStageName.getAll()) { stage ->
                    val isCompleted = uiState.stages.any { it.stageName == stage }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape)
                                .background(if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                            else Text("${stage.order + 1}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(stage.displayName, style = MaterialTheme.typography.labelSmall, color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            if (uiState.stages.isEmpty() && !uiState.isLoading) {
                EmptyState(title = "No stages yet", subtitle = "Add your first work-in-progress stage")
            } else {
                LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(uiState.stages, key = { it.id }) { stage ->
                        TimelineStageCard(stage = stage)
                    }
                }
            }
        }

        if (uiState.showAddDialog) {
            AddStageDialog(
                existingStages = uiState.stages.map { it.stageName },
                onDismiss = { viewModel.hideAddDialog() },
                onAdd = { stageName, photoUri, caption ->
                    val context = LocalContext // handled below
                    viewModel.addStage(stageName, photoUri, caption, java.io.File(""))
                }
            )
        }
    }
}

@Composable
private fun TimelineStageCard(stage: com.shilpakala.showcase.domain.model.WipStage) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${stage.stageName.order + 1}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(stage.stageName.displayName, style = MaterialTheme.typography.titleSmall)
                    Text(DateTimeUtils.formatDisplayDate(stage.capturedAt), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (stage.photoUri != null) {
                Spacer(Modifier.height(12.dp))
                AsyncImage(model = stage.photoUri, contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
            }
            if (stage.caption.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(stage.caption, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun AddStageDialog(existingStages: List<WipStageName>, onDismiss: () -> Unit, onAdd: (WipStageName, Uri?, String) -> Unit) {
    var selectedStage by remember { mutableStateOf(WipStageName.getAll().firstOrNull { it !in existingStages }) }
    var caption by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> photoUri = uri }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_stage)) },
        text = {
            Column {
                WipStageName.getAll().filter { it !in existingStages }.forEach { stage ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        RadioButton(selected = selectedStage == stage, onClick = { selectedStage = stage })
                        Spacer(Modifier.width(8.dp))
                        Text(stage.displayName)
                    }
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (photoUri != null) "Photo selected" else "Add Photo")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = caption, onValueChange = { caption = it.take(500) }, label = { Text(stringResource(R.string.enter_caption)) }, modifier = Modifier.fillMaxWidth(), maxLines = 3)
            }
        },
        confirmButton = {
            TextButton(onClick = { selectedStage?.let { onAdd(it, photoUri, caption) } }, enabled = selectedStage != null) { Text(stringResource(R.string.save)) }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}
