package com.shilpakala.showcase.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.ui.components.GoldButton
import com.shilpakala.showcase.domain.model.Language
import kotlinx.coroutines.launch

data class OnboardingPage(val emoji: String, val titleRes: Int, val descRes: Int)

private val pages = listOf(
    OnboardingPage("🏛️", R.string.onboarding_title_1, R.string.onboarding_desc_1),
    OnboardingPage("🤝", R.string.onboarding_title_2, R.string.onboarding_desc_2),
    OnboardingPage("🎨", R.string.onboarding_title_3, R.string.onboarding_desc_3)
)

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onNavigateToProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showLanguageSelector by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AnimatedVisibility(visible = showLanguageSelector, enter = fadeIn(), exit = fadeOut()) {
            LanguageSelectionContent(
                selectedLanguage = uiState.selectedLanguage,
                onLanguageSelected = { language ->
                    viewModel.selectLanguage(language)
                    showLanguageSelector = false
                }
            )
        }
        AnimatedVisibility(visible = !showLanguageSelector, enter = fadeIn(), exit = fadeOut()) {
            OnboardingPagerContent(
                uiState = uiState,
                onPageChanged = { viewModel.setPage(it) },
                onFinish = {
                    viewModel.completeOnboarding()
                    onNavigateToProfile()
                }
            )
        }
    }
}

@Composable
private fun LanguageSelectionContent(
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🏛️", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Shilpa-Kala Showcase",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.select_language),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Language.entries.forEach { language ->
            LanguageCard(
                language = language,
                isSelected = selectedLanguage == language,
                onClick = { onLanguageSelected(language) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun LanguageCard(language: Language, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(64.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = language.displayName,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingPagerContent(
    uiState: OnboardingUiState,
    onPageChanged: (Int) -> Unit,
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) { onPageChanged(pagerState.currentPage) }

    Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        // Skip button
        Box(modifier = Modifier.fillMaxWidth()) {
            if (pagerState.currentPage < pages.lastIndex) {
                TextButton(onClick = onFinish, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Text(stringResource(R.string.skip), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val p = pages[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = p.emoji, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(p.titleRes),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(p.descRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Page indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        GoldButton(
            text = if (pagerState.currentPage == pages.lastIndex) stringResource(R.string.get_started)
            else stringResource(R.string.next),
            onClick = {
                if (pagerState.currentPage == pages.lastIndex) onFinish()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
