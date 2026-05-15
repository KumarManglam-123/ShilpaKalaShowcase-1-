package com.shilpakala.showcase.core.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.openUrl(url: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            true
        } else {
            false
        }
    } catch (e: Exception) {
        false
    }
}
