package com.shilpakala.showcase.core.constants

object AppConstants {
    const val DATABASE_NAME = "shilpakala_db"
    const val DATABASE_VERSION = 1

    const val DATASTORE_NAME = "shilpakala_prefs"

    const val MAX_ARTWORK_IMAGES = 30
    const val IMAGE_MAX_DIMENSION = 2048
    const val IMAGE_COMPRESSION_QUALITY = 85
    const val THUMBNAIL_SIZE = 512

    const val PROFILE_PHOTO_MAX_SIZE = 1024
    const val PROFILE_PHOTO_QUALITY = 90

    const val SEARCH_DEBOUNCE_MS = 300L
    const val PULL_REFRESH_COOLDOWN_MS = 2000L

    const val ZOOM_MIN = 1f
    const val ZOOM_MAX = 5f
    const val DOUBLE_TAP_ZOOM = 2.5f

    const val SHILPI_ID_PREFIX = "SK"
    const val PRODUCT_ID_PREFIX = "SKP"
    const val STATE_CODE_KARNATAKA = "KA"

    const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/"
    const val GEMINI_TIMEOUT_SECONDS = 30L
    const val GEMINI_MAX_RETRIES = 3

    const val SYNC_WORK_NAME = "shilpakala_sync"
    const val CACHE_CLEANUP_WORK_NAME = "shilpakala_cache_cleanup"
    const val SYNC_INTERVAL_HOURS = 6L
    const val CACHE_MAX_AGE_DAYS = 30L

    const val WHATSAPP_PACKAGE = "com.whatsapp"
    const val WHATSAPP_BUSINESS_PACKAGE = "com.whatsapp.w4b"

    const val ANIMATION_DURATION_SHORT = 200
    const val ANIMATION_DURATION_MEDIUM = 400
    const val ANIMATION_DURATION_LONG = 600

    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = 5

    const val MIN_TOUCH_TARGET_DP = 48
}
