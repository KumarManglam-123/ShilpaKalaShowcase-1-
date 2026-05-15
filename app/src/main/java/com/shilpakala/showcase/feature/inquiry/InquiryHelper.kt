package com.shilpakala.showcase.feature.inquiry

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.core.extensions.isPackageInstalled
import com.shilpakala.showcase.core.extensions.showToast

/**
 * Handles WhatsApp inquiry flow with SMS and share sheet fallbacks.
 */
object InquiryHelper {

    fun sendInquiry(context: Context, productId: String, phoneNumber: String? = null) {
        val message = "Hello, I am interested in [Product ID: $productId]"
        val encodedMessage = Uri.encode(message)

        // Try WhatsApp
        if (tryWhatsApp(context, encodedMessage, phoneNumber)) return

        // Fallback: SMS
        if (trySms(context, message, phoneNumber)) return

        // Fallback: Email
        if (tryEmail(context, message, productId)) return

        // Final fallback: Share sheet
        tryShareSheet(context, message)
    }

    private fun tryWhatsApp(context: Context, encodedMessage: String, phoneNumber: String?): Boolean {
        val packages = listOf(AppConstants.WHATSAPP_PACKAGE, AppConstants.WHATSAPP_BUSINESS_PACKAGE)
        for (pkg in packages) {
            if (context.isPackageInstalled(pkg)) {
                try {
                    val url = if (phoneNumber != null) {
                        "https://wa.me/$phoneNumber?text=$encodedMessage"
                    } else {
                        "https://wa.me/?text=$encodedMessage"
                    }
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply { setPackage(pkg) }
                    context.startActivity(intent)
                    return true
                } catch (_: Exception) { }
            }
        }
        return false
    }

    private fun trySms(context: Context, message: String, phoneNumber: String?): Boolean {
        return try {
            val uri = if (phoneNumber != null) Uri.parse("sms:$phoneNumber") else Uri.parse("sms:")
            val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
                putExtra("sms_body", message)
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                true
            } else false
        } catch (_: Exception) { false }
    }

    private fun tryEmail(context: Context, message: String, productId: String): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
                putExtra(Intent.EXTRA_SUBJECT, "Inquiry for $productId")
                putExtra(Intent.EXTRA_TEXT, message)
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                true
            } else false
        } catch (_: Exception) { false }
    }

    private fun tryShareSheet(context: Context, message: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            context.startActivity(Intent.createChooser(intent, "Share via"))
        } catch (e: Exception) {
            context.showToast("No messaging app found")
        }
    }
}
