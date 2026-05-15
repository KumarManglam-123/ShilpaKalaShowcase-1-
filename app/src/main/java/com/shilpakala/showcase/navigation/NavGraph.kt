package com.shilpakala.showcase.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.feature.artwork.add.AddArtworkScreen
import com.shilpakala.showcase.feature.artwork.add.AddArtworkViewModel
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailScreen
import com.shilpakala.showcase.feature.artwork.detail.ArtworkDetailViewModel
import com.shilpakala.showcase.feature.artwork.list.ArtworkListScreen
import com.shilpakala.showcase.feature.artwork.list.ArtworkListViewModel
import com.shilpakala.showcase.feature.artwork.viewer.ImageViewerScreen
import com.shilpakala.showcase.feature.heritage.HeritageDetailScreen
import com.shilpakala.showcase.feature.heritage.HeritageListScreen
import com.shilpakala.showcase.feature.heritage.HeritageViewModel
import com.shilpakala.showcase.feature.home.HomeScreen
import com.shilpakala.showcase.feature.home.HomeViewModel
import com.shilpakala.showcase.feature.onboarding.OnboardingScreen
import com.shilpakala.showcase.feature.onboarding.OnboardingViewModel
import com.shilpakala.showcase.feature.profile.ProfileViewModel
import com.shilpakala.showcase.feature.profile.ProfileWizardScreen
import com.shilpakala.showcase.feature.search.SearchScreen
import com.shilpakala.showcase.feature.search.SearchViewModel
import com.shilpakala.showcase.feature.settings.SettingsScreen
import com.shilpakala.showcase.feature.settings.SettingsViewModel
import com.shilpakala.showcase.feature.splash.SplashScreen
import com.shilpakala.showcase.feature.wip.WipTimelineScreen
import com.shilpakala.showcase.feature.wip.WipViewModel

private const val ANIM_DURATION = AppConstants.ANIMATION_DURATION_MEDIUM

@Composable
fun ShilpaKalaNavGraph(
    isOnline: Boolean,
    onSplashFinished: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) + fadeIn(tween(ANIM_DURATION))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIM_DURATION)) + fadeOut(tween(ANIM_DURATION))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) + fadeIn(tween(ANIM_DURATION))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIM_DURATION)) + fadeOut(tween(ANIM_DURATION))
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    onSplashFinished()
                    navController.navigate(Screen.Onboarding.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
                },
                onNavigateToHome = {
                    onSplashFinished()
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Splash.route) { inclusive = true } }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                viewModel = viewModel,
                onNavigateToProfile = {
                    navController.navigate(Screen.ProfileWizard.route) { popUpTo(Screen.Onboarding.route) { inclusive = true } }
                }
            )
        }

        composable(Screen.ProfileWizard.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileWizardScreen(
                viewModel = viewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.ProfileWizard.route) { inclusive = true } }
                }
            )
        }

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                isOnline = isOnline,
                onNavigateToArtworkList = { shilpiId -> navController.navigate(Screen.ArtworkList.createRoute(shilpiId)) },
                onNavigateToArtworkDetail = { artworkId -> navController.navigate(Screen.ArtworkDetail.createRoute(artworkId)) },
                onNavigateToHeritage = { navController.navigate(Screen.HeritageList.route) },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToAddArtwork = { shilpiId -> navController.navigate(Screen.AddArtwork.createRoute(shilpiId)) }
            )
        }

        composable(
            route = Screen.ArtworkList.route,
            arguments = listOf(navArgument("shilpiId") { type = NavType.StringType })
        ) {
            val viewModel: ArtworkListViewModel = hiltViewModel()
            ArtworkListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { artworkId -> navController.navigate(Screen.ArtworkDetail.createRoute(artworkId)) },
                onNavigateToAdd = { shilpiId -> navController.navigate(Screen.AddArtwork.createRoute(shilpiId)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ArtworkDetail.route,
            arguments = listOf(navArgument("artworkId") { type = NavType.StringType })
        ) {
            val viewModel: ArtworkDetailViewModel = hiltViewModel()
            ArtworkDetailScreen(
                viewModel = viewModel,
                onNavigateToImageViewer = { uri -> navController.navigate(Screen.ImageViewer.createRoute(uri)) },
                onNavigateToWipTimeline = { artworkId -> navController.navigate(Screen.WipTimeline.createRoute(artworkId)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.AddArtwork.route,
            arguments = listOf(navArgument("shilpiId") { type = NavType.StringType })
        ) {
            val viewModel: AddArtworkViewModel = hiltViewModel()
            AddArtworkScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onArtworkAdded = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ImageViewer.route,
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUri = backStackEntry.arguments?.getString("imageUri") ?: ""
            val uri = java.net.URLDecoder.decode(encodedUri, "UTF-8")
            ImageViewerScreen(imageUri = uri, onDismiss = { navController.popBackStack() })
        }

        composable(
            route = Screen.WipTimeline.route,
            arguments = listOf(navArgument("artworkId") { type = NavType.StringType })
        ) {
            val viewModel: WipViewModel = hiltViewModel()
            WipTimelineScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.HeritageList.route) {
            val viewModel: HeritageViewModel = hiltViewModel()
            HeritageListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { id -> navController.navigate(Screen.HeritageDetail.createRoute(id)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.HeritageDetail.route,
            arguments = listOf(navArgument("heritageId") { type = NavType.StringType })
        ) {
            val viewModel: HeritageViewModel = hiltViewModel()
            val heritageId = it.arguments?.getString("heritageId") ?: ""
            HeritageDetailScreen(viewModel = viewModel, heritageId = heritageId, onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Search.route) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                viewModel = viewModel,
                onNavigateToDetail = { artworkId -> navController.navigate(Screen.ArtworkDetail.createRoute(artworkId)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
    }
}
