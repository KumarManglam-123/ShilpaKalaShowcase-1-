package com.shilpakala.showcase.feature.artwork.viewer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shilpakala.showcase.R
import com.shilpakala.showcase.core.constants.AppConstants

@Composable
fun ImageViewerScreen(imageUri: String, onDismiss: () -> Unit) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = stringResource(R.string.cd_artwork_image),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale, scaleY = scale,
                    translationX = offset.x, translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = (scale * zoom).coerceIn(AppConstants.ZOOM_MIN, AppConstants.ZOOM_MAX)
                        if (scale > 1f) {
                            offset = Offset(offset.x + pan.x, offset.y + pan.y)
                        } else {
                            offset = Offset.Zero
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (scale > 1f) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scale = AppConstants.DOUBLE_TAP_ZOOM
                            }
                        }
                    )
                },
            contentScale = ContentScale.Fit
        )

        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).statusBarsPadding()
        ) {
            Icon(Icons.Default.Close, stringResource(R.string.cd_close), tint = Color.White)
        }
    }
}
