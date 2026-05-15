package com.shilpakala.showcase.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f, targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing)), label = "shimmer"
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
    Box(modifier = modifier.background(brush).clip(RoundedCornerShape(8.dp)))
}

@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        ShimmerEffect(modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerEffect(modifier = Modifier.fillMaxWidth(0.7f).height(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        ShimmerEffect(modifier = Modifier.fillMaxWidth(0.4f).height(12.dp))
    }
}
