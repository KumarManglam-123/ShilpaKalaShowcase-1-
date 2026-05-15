package com.shilpakala.showcase.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.domain.repository.HeritageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class CacheCleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val heritageRepository: HeritageRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Clean old image cache
            val cacheDir = applicationContext.cacheDir
            val maxAge = AppConstants.CACHE_MAX_AGE_DAYS * 24 * 60 * 60 * 1000L
            val cutoff = System.currentTimeMillis() - maxAge

            cleanOldFiles(File(cacheDir, "image_cache"), cutoff)
            cleanOldFiles(File(cacheDir, "artwork_images"), cutoff)
            cleanOldFiles(File(cacheDir, "wip_images"), cutoff)

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun cleanOldFiles(directory: File, cutoffTimestamp: Long) {
        if (!directory.exists()) return
        directory.listFiles()?.forEach { file ->
            if (file.lastModified() < cutoffTimestamp) {
                file.delete()
            }
        }
    }
}
