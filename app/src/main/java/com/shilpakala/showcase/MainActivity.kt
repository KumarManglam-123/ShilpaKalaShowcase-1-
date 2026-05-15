package com.shilpakala.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shilpakala.showcase.core.network.ConnectivityObserver
import com.shilpakala.showcase.core.theme.ShilpaKalaTheme
import com.shilpakala.showcase.data.local.datastore.PreferencesManager
import com.shilpakala.showcase.navigation.ShilpaKalaNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Keep splash visible while checking onboarding state
        var keepSplashVisible = true
        splashScreen.setKeepOnScreenCondition { keepSplashVisible }

        setContent {
            val isDarkMode by preferencesManager.isDarkMode.collectAsStateWithLifecycle(
                initialValue = false
            )
            val isOnline by connectivityObserver.isConnected.collectAsStateWithLifecycle(
                initialValue = true
            )

            ShilpaKalaTheme(darkTheme = isDarkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShilpaKalaNavGraph(
                        isOnline = isOnline,
                        onSplashFinished = { keepSplashVisible = false }
                    )
                }
            }
        }
    }
}
