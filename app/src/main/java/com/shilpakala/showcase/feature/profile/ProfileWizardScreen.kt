package com.shilpakala.showcase.feature.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.ui.components.GoldButton

private val specialisations = listOf(
    "Stone Sculpture", "Wood Carving", "Temple Architecture",
    "Idol Making", "Relief Work", "Decorative Art"
)

@Composable
fun ProfileWizardScreen(viewModel: ProfileViewModel, onNavigateToHome: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.isComplete) {
        if (uiState.isComplete) onNavigateToHome()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .padding(24.dp).statusBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.profile_setup),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.step_of, uiState.currentStep + 1, uiState.totalSteps),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Progress indicator
        LinearProgressIndicator(
            progress = { (uiState.currentStep + 1f) / uiState.totalSteps },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outlineVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(targetState = uiState.currentStep, label = "step") { step ->
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    when (step) {
                        0 -> NameStep(uiState, viewModel)
                        1 -> LocationStep(uiState, viewModel)
                        2 -> SpecialisationStep(uiState, viewModel)
                        3 -> PhotoStep(uiState, viewModel)
                    }
                }
            }
        }

        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (uiState.currentStep > 0) {
                OutlinedButton(onClick = { viewModel.previousStep() }) { Text("Back") }
            } else { Spacer(Modifier) }

            GoldButton(
                text = if (uiState.currentStep == uiState.totalSteps - 1) stringResource(R.string.finish)
                else stringResource(R.string.continue_text),
                isLoading = uiState.isLoading,
                onClick = {
                    if (uiState.currentStep == uiState.totalSteps - 1) viewModel.finishProfile()
                    else viewModel.nextStep()
                }
            )
        }
    }
}

@Composable
private fun NameStep(uiState: ProfileWizardState, viewModel: ProfileViewModel) {
    Column {
        Text(stringResource(R.string.step_name), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = uiState.name, onValueChange = { viewModel.updateName(it) },
            label = { Text(stringResource(R.string.enter_name)) },
            isError = uiState.nameError != null,
            supportingText = uiState.nameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(), singleLine = true
        )
    }
}

@Composable
private fun LocationStep(uiState: ProfileWizardState, viewModel: ProfileViewModel) {
    Column {
        Text(stringResource(R.string.step_location), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = uiState.village, onValueChange = { viewModel.updateVillage(it) },
            label = { Text(stringResource(R.string.enter_village)) },
            modifier = Modifier.fillMaxWidth(), singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.district, onValueChange = { viewModel.updateDistrict(it) },
            label = { Text(stringResource(R.string.enter_district)) },
            isError = uiState.locationError != null,
            supportingText = uiState.locationError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(), singleLine = true
        )
    }
}

@Composable
private fun SpecialisationStep(uiState: ProfileWizardState, viewModel: ProfileViewModel) {
    Column {
        Text(stringResource(R.string.step_specialisation), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(24.dp))
        specialisations.forEach { spec ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    .clickable { viewModel.updateSpecialisation(spec) },
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.specialisation == spec) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = uiState.specialisation == spec, onClick = { viewModel.updateSpecialisation(spec) })
                    Spacer(Modifier.width(12.dp))
                    Text(spec, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
private fun PhotoStep(uiState: ProfileWizardState, viewModel: ProfileViewModel) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateProfilePhoto(it, context.cacheDir) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.step_photo), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier.size(160.dp).clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.profilePhotoUri != null) {
                AsyncImage(
                    model = uiState.profilePhotoUri, contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Default.CameraAlt, "Add photo", modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
            Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.choose_gallery))
        }
    }
}
