package com.shilpakala.showcase.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shilpakala.showcase.data.local.datastore.PreferencesManager
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.domain.repository.HeritageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val heritageRepository: HeritageRepository,
    private val preferencesManager: PreferencesManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Sync heritage data
            heritageRepository.refreshHeritage("en")
            heritageRepository.refreshHeritage("kn")
            preferencesManager.setLastSyncTimestamp(DateTimeUtils.formatIso(DateTimeUtils.currentTimeMillis()))
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}
