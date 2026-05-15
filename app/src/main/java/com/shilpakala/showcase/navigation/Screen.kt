package com.shilpakala.showcase.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object ProfileWizard : Screen("profile_wizard")
    data object Home : Screen("home")
    data object ArtworkList : Screen("artwork_list/{shilpiId}") {
        fun createRoute(shilpiId: String) = "artwork_list/$shilpiId"
    }
    data object ArtworkDetail : Screen("artwork_detail/{artworkId}") {
        fun createRoute(artworkId: String) = "artwork_detail/$artworkId"
    }
    data object AddArtwork : Screen("add_artwork/{shilpiId}") {
        fun createRoute(shilpiId: String) = "add_artwork/$shilpiId"
    }
    data object ImageViewer : Screen("image_viewer/{imageUri}") {
        fun createRoute(imageUri: String) = "image_viewer/${java.net.URLEncoder.encode(imageUri, "UTF-8")}"
    }
    data object WipTimeline : Screen("wip_timeline/{artworkId}") {
        fun createRoute(artworkId: String) = "wip_timeline/$artworkId"
    }
    data object HeritageList : Screen("heritage_list")
    data object HeritageDetail : Screen("heritage_detail/{heritageId}") {
        fun createRoute(heritageId: String) = "heritage_detail/$heritageId"
    }
    data object Search : Screen("search")
    data object Settings : Screen("settings")
}
