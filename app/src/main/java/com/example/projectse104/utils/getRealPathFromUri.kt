package com.example.projectse104.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File

fun getRealPathFromUri(context: Context, uri: Uri): String? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val file = File(context.cacheDir, "profile_image_${System.currentTimeMillis()}.jpg")
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            file.absolutePath
        } ?: throw Exception("Không thể mở luồng đầu vào từ URI")
    } catch (e: Exception) {
        Log.e("FileUtils", "Không thể chuyển đổi URI: ${e.message}", e)
        null
    }
}