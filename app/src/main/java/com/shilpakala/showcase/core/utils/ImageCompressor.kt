package com.shilpakala.showcase.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.shilpakala.showcase.core.constants.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles image compression, rotation correction, and downsampling.
 * Prevents OOM by calculating appropriate sample sizes before decoding.
 */
@Singleton
class ImageCompressor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * Compresses an image URI to a file with controlled dimensions and quality.
     * Handles EXIF rotation, large images, and corrupted URIs.
     *
     * @param uri Source image URI
     * @param directory Target directory
     * @param fileName Target filename
     * @param maxDimension Maximum width or height
     * @param quality JPEG compression quality (0-100)
     * @return Compressed image file, or null if processing fails
     */
    suspend fun compressImage(
        uri: Uri,
        directory: File,
        fileName: String,
        maxDimension: Int = AppConstants.IMAGE_MAX_DIMENSION,
        quality: Int = AppConstants.IMAGE_COMPRESSION_QUALITY
    ): File? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return@withContext null

            // Step 1: Read dimensions without loading full bitmap
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()

            val originalWidth = options.outWidth
            val originalHeight = options.outHeight

            if (originalWidth <= 0 || originalHeight <= 0) {
                return@withContext null
            }

            // Step 2: Calculate sample size for memory-efficient loading
            options.inSampleSize = calculateSampleSize(originalWidth, originalHeight, maxDimension)
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            // Step 3: Decode with calculated sample size
            val secondStream = context.contentResolver.openInputStream(uri)
                ?: return@withContext null
            val sampledBitmap = BitmapFactory.decodeStream(secondStream, null, options)
            secondStream.close()

            if (sampledBitmap == null) {
                return@withContext null
            }

            // Step 4: Scale to exact target dimensions
            val scaledBitmap = scaleBitmap(sampledBitmap, maxDimension)

            // Step 5: Apply EXIF rotation correction
            val rotatedBitmap = correctRotation(uri, scaledBitmap)

            // Step 6: Save compressed file
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val outputFile = File(directory, fileName)
            FileOutputStream(outputFile).use { outputStream ->
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                outputStream.flush()
            }

            // Clean up bitmaps
            if (rotatedBitmap !== scaledBitmap) rotatedBitmap.recycle()
            if (scaledBitmap !== sampledBitmap) scaledBitmap.recycle()
            sampledBitmap.recycle()

            outputFile
        } catch (e: IOException) {
            null
        } catch (e: OutOfMemoryError) {
            System.gc()
            null
        }
    }

    /**
     * Creates a thumbnail from an image URI.
     */
    suspend fun createThumbnail(
        uri: Uri,
        directory: File,
        fileName: String
    ): File? {
        return compressImage(
            uri = uri,
            directory = directory,
            fileName = "thumb_$fileName",
            maxDimension = AppConstants.THUMBNAIL_SIZE,
            quality = 75
        )
    }

    private fun calculateSampleSize(
        width: Int,
        height: Int,
        maxDimension: Int
    ): Int {
        var sampleSize = 1
        if (width > maxDimension || height > maxDimension) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while ((halfWidth / sampleSize) >= maxDimension &&
                (halfHeight / sampleSize) >= maxDimension
            ) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }

    private fun scaleBitmap(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxDimension && height <= maxDimension) {
            return bitmap
        }

        val ratio = minOf(
            maxDimension.toFloat() / width,
            maxDimension.toFloat() / height
        )

        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun correctRotation(uri: Uri, bitmap: Bitmap): Bitmap {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return bitmap
            val exif = ExifInterface(inputStream)
            inputStream.close()

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val rotationDegrees = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> return bitmap
            }

            val matrix = Matrix().apply { postRotate(rotationDegrees) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            bitmap
        }
    }
}
