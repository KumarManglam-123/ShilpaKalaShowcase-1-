package com.shilpakala.showcase.feature.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shilpakala.showcase.data.local.datastore.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    suspend fun shouldShowOnboarding(): Boolean {
        return !preferencesManager.isOnboardingCompleted.first()
    }
}

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = "alpha"
    )
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.7f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing), label = "scale"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1800)
        if (viewModel.shouldShowOnboarding()) onNavigateToOnboarding()
        else onNavigateToHome()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim.value).scale(scaleAnim.value)
        ) {
            Text(
                text = "🏛️",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Shilpa-Kala",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Light, letterSpacing = 4.sp),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "SHOWCASE",
                style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 8.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
